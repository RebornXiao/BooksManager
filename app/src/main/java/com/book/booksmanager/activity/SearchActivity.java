package com.book.booksmanager.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.book.booksmanager.R;
import com.book.booksmanager.data.BookDBBean;
import com.book.booksmanager.data.BookDetail;
import com.book.booksmanager.data.CategoryInfo;
import com.book.booksmanager.net.Httpmanage;
import com.book.booksmanager.utils.MLog;
import com.book.booksmanager.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by baby on 2016/6/18.
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {


    @Bind(R.id.tv_begin_search)
    TextView tvBeginSearch;
    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.rl_search_all)
    RelativeLayout rlSearchAll;
    @Bind(R.id.iv_book_detail)
    ImageView ivBookDetail;
    @Bind(R.id.tv_book_title)
    TextView tvBookTitle;
    @Bind(R.id.tv_book_chubanshe)
    TextView tvBookChubanshe;
    @Bind(R.id.tv_book_fenlei)
    TextView tvBookFenlei;
    @Bind(R.id.tv_book_isbn)
    TextView tvBookIsbn;
    @Bind(R.id.tv_book_zuozhe)
    TextView tvBookZuozhe;
    @Bind(R.id.tv_book_fanyi)
    TextView tvBookFanyi;
    @Bind(R.id.tv_book_time)
    TextView tvBookTime;
    @Bind(R.id.tv_book_price)
    TextView tvBookPrice;
    @Bind(R.id.ll_book_detail)
    LinearLayout llBookDetail;
    @Bind(R.id.rl_book_top)
    RelativeLayout rlBookTop;
    @Bind(R.id.tv_book_biaoqian)
    TextView tvBookBiaoqian;
    @Bind(R.id.tv_book_biaoqian_detail)
    TextView tvBookBiaoqianDetail;
    @Bind(R.id.tv_book_pingfen)
    TextView tvBookPingfen;
    @Bind(R.id.tv_book_pingfen_num)
    TextView tvBookPingfenNum;
    @Bind(R.id.rl_book_minddle)
    RelativeLayout rlBookMinddle;
    @Bind(R.id.tv_book_detail)
    TextView tvBookDetail;
    @Bind(R.id.rl_book_result)
    RelativeLayout rlBookResult;
    @Bind(R.id.rl_find_result)
    RelativeLayout rlFindResult;
    @Bind(R.id.tv_add_db)
    TextView tvAddDb;
    private BookDBBean bookDBBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchView.setQueryHint("请输入您要查找的ISBN");
    }

    @OnClick(R.id.tv_begin_search)
    void search() {//7508314131
        String isbn = searchView.getQuery().toString().trim();
        searchData(isbn);
    }

    private void searchData(String isbn) {
        if (!TextUtils.isEmpty(isbn.toString().trim())) {
            if (app.isNetworkAvailable()) {
                showTipsDialogs("正在查找");
                Httpmanage.getInstance().searchBook(isbn, new Httpmanage.DataListener() {
                    @Override
                    public void onData(String result) {
                        MLog.e("返回:" + result);
                        BookDetail bookDetail = null;
                        try {
                            bookDetail = JSON.parseObject(result, BookDetail.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        final BookDetail bookDetailTemp = bookDetail;
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dismissTipsDialogs();
                                if (bookDetailTemp != null && !TextUtils.isEmpty(bookDetailTemp.getTitle())) {
                                    rlBookResult.setVisibility(View.VISIBLE);
                                    rlFindResult.setVisibility(View.INVISIBLE);
                                    bookDBBean = new BookDBBean(bookDetailTemp, "");
                                    initData(bookDBBean);
                                } else {
                                    ToastUtils.show(mContext, "没有找到这本书！");
                                    rlBookResult.setVisibility(View.INVISIBLE);
                                    rlFindResult.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                    }
                });
            } else {
                ToastUtils.show(mContext, "没有网络~");
            }
        } else {
            ToastUtils.show(mContext, "请输入ISBN码！");
        }
    }


    @OnClick(R.id.tv_add_db)
    void add() {//7508314131
        if (bookDBBean != null && !TextUtils.isEmpty(bookDBBean.getTitle())) {
            app.savaBook(bookDBBean);
            CategoryInfo categoryInfo = app.categoryDAO.queryCourseByTitle(bookDBBean.getCategory());
            BookDBBean bookDBBeanFind = app.bookDetailDAO.queryCourseByTitle(bookDBBean.getTitle(), bookDBBean.getCategory());
            if (categoryInfo != null) {
                if (bookDBBeanFind == null) {
                    categoryInfo.setNums((Integer.parseInt(categoryInfo.getNums()) + 1) + "");
                    app.categoryDAO.update(categoryInfo);
                }
            } else {
                categoryInfo = new CategoryInfo("1", bookDBBean.getCategory());
                app.categoryDAO.insertCategory(categoryInfo);
            }
            ToastUtils.show(mContext, "保存成功");
            activityUtil.jumpBackTo(MainActivity.class);
            finish();
        } else {
            ToastUtils.show(mContext, "请搜索正确的书籍！");
        }
    }

    private void initData(BookDBBean bookDBBean) {
        tvBookTitle.setText(bookDBBean.getTitle());
        tvBookChubanshe.setText("出版社:" + bookDBBean.getPublisher());
        tvBookFenlei.setText("分类:" + bookDBBean.getCategory());
        tvBookZuozhe.setText("作者:" + bookDBBean.getAuthor());
        tvBookIsbn.setText("ISBN:" + bookDBBean.getIsbn10());
        tvBookTime.setText("出版时间:" + bookDBBean.getPubdate());
        tvBookPrice.setText("价格:" + bookDBBean.getPrice());
        tvBookBiaoqianDetail.setText(bookDBBean.getTags());
        tvBookPingfenNum.setText("共" + bookDBBean.getNumRaters() + "人评分，平均得分：" + bookDBBean.getAverage() + "/" + bookDBBean.getMax());
        Picasso.with(mContext).load(bookDBBean.getImage()).into(ivBookDetail);
        tvBookDetail.setText(bookDBBean.getSummary());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        MLog.e("---->change:" + newText);

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityUtil.jumpBackTo(MainActivity.class);
    }
}
