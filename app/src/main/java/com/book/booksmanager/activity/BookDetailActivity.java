package com.book.booksmanager.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.book.booksmanager.R;
import com.book.booksmanager.data.BookDBBean;
import com.book.booksmanager.data.BookDetail;
import com.book.booksmanager.data.BookInfoDB;
import com.book.booksmanager.data.CategoryInfo;
import com.book.booksmanager.utils.MLog;
import com.book.booksmanager.view.EditBookView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 书籍详情
 */
public class BookDetailActivity extends BaseActivity {

    @Bind(R.id.iv_book_detail)
    ImageView ivBookDetail;
    @Bind(R.id.tv_book_title)
    TextView tvBookTitle;
    @Bind(R.id.tv_book_chubanshe)
    TextView tvBookChubanshe;
    @Bind(R.id.tv_book_fenlei)
    TextView tvBookFenlei;
    @Bind(R.id.tv_book_zuozhe)
    TextView tvBookZuozhe;
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
    @Bind(R.id.tv_book_isbn)
    TextView tvBookIsbn;
    @Bind(R.id.tv_book_time)
    TextView tvBookTime;
    @Bind(R.id.tv_book_price)
    TextView tvBookPrice;
    @Bind(R.id.sv_book_detail_all)
    ScrollView svBookDetailAll;
    @Bind(R.id.rl_book_detail_all)
    RelativeLayout rlBookDetailAll;
    @Bind(R.id.rl_book_choose_all)
    RelativeLayout rlBookChooseAll;
    private BookDBBean bookDBBean;
    private RelativeLayout.LayoutParams layoutParams;
    private EditBookView editBookView;
    private String category;//进入页面的初始化分类

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        registerForContextMenu(rlBookChooseAll);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        boolean isHava = getIntent().getExtras().getBoolean("havaThisBook");
        if (isHava) {
            bookDBBean = (BookDBBean) getIntent().getExtras().getSerializable("bookInfo");
        } else {
            String result = getIntent().getExtras().getString("book");
            category = getIntent().getExtras().getString("fenlei");
            BookDetail bookDetail = JSON.parseObject(result, BookDetail.class);
            bookDBBean = new BookDBBean(bookDetail, category);
            app.savaBook(bookDBBean);
        }
        category = bookDBBean.getCategory();
        initData(bookDBBean);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MLog.e("创建contextmenu");
        menu.add(0, 1, Menu.NONE, "修改图书");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 得到当前被选中的item信息
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                if (editBookView == null) {
                    editBookView = new EditBookView(mContext);
                    editBookView.setBookDBBean(bookDBBean, new EditBookView.SavaBookListener() {
                        @Override
                        public void sava(BookDBBean bookDBBeanNew) {
                            initData(bookDBBeanNew);
                            if (category.equals(bookDBBeanNew.getCategory())) {//书本类别没有改变,其他信息发生改变
                                app.bookDetailDAO.deleteInfo(bookDBBean.getTitle());
                                app.savaBook(bookDBBeanNew);
                                /**
                                 * 更新类别数据表
                                 */
                                BookInfoDB bookInfoDB = new BookInfoDB(bookDBBeanNew);
                                app.categoryDetailDAO.insertCategoryDetail(bookInfoDB);

                            } else {//书本类别 改变
                                BookDBBean bookDBFind = app.bookDetailDAO.queryCourseByTitle(bookDBBeanNew.getTitle(), bookDBBeanNew.getCategory());
                                app.bookDetailDAO.deleteInfo(bookDBBean.getTitle());
                                app.savaBook(bookDBBeanNew);

                                if (bookDBFind == null) {
                                    //一本新书,书名和类别都被更改了
                                    /**
                                     * 1.入库
                                     * 2.检查 这种类别有没有书籍   没有的话  ，就要添加类别  ，同时添加类别详情，更新两张表
                                     */
                                    CategoryInfo categoryInfo = app.categoryDAO.queryCourseByTitle(bookDBBeanNew.getCategory());

                                    /**
                                     * 旧类别数据更新，
                                     */
                                    CategoryInfo categoryInfoOld = app.categoryDAO.queryCourseByTitle(category);
                                    categoryInfoOld.setNums((Integer.parseInt(categoryInfoOld.getNums()) - 1) + "");
                                    app.savaCate(categoryInfoOld);
                                    app.categoryDetailDAO.deleteInfo(bookDBBean.getTitle());

                                    /**
                                     * 更改后新类别数据更新
                                     */
                                    if (categoryInfo != null) {//已经存在的类别
                                        categoryInfo.setNums((Integer.parseInt(categoryInfo.getNums()) + 1) + "");
                                    } else {//不存在的类别
                                        categoryInfo = new CategoryInfo();
                                        categoryInfo.setNums("1");
                                        categoryInfo.setTitle(bookDBBeanNew.getCategory());
                                        app.categoryDAO.insertCategory(categoryInfo);
                                    }

                                    /**
                                     * 更新类别数据表
                                     */
                                    BookInfoDB bookInfoDB = new BookInfoDB(bookDBBeanNew);
                                    app.categoryDetailDAO.insertCategoryDetail(bookInfoDB);
                                }
                            }
                            rlBookDetailAll.removeView(editBookView);
                        }
                    });
                }
                rlBookDetailAll.addView(editBookView, layoutParams);
                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(rlBookChooseAll);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (editBookView != null && findViewById(R.id.rl_view_edit_book) != null) {
                rlBookDetailAll.removeView(editBookView);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
