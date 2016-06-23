package com.book.booksmanager.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.book.booksmanager.R;
import com.book.booksmanager.adapter.BookGridAdapter;
import com.book.booksmanager.data.BookDBBean;
import com.book.booksmanager.data.BookInfo;
import com.book.booksmanager.data.BookInfoDB;
import com.book.booksmanager.data.BookTitleData;
import com.book.booksmanager.data.CategoryInfo;
import com.book.booksmanager.net.Httpmanage;
import com.book.booksmanager.utils.MLog;
import com.book.booksmanager.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_main_title)
    RelativeLayout rlMainTitle;
    @Bind(R.id.gv_book_list)
    GridView gvBookList;
    private BookGridAdapter adapter;
    private String category;
    private List<BookInfoDB> bookInfoDBs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (adapter == null) {
            adapter = new BookGridAdapter(savedInstanceState, mContext);
        }
        adapter.setAdapterView(gvBookList);
        gvBookList.setOnItemClickListener(this);
        adapter.setListenr(new BookGridAdapter.BookManageListenr() {
            @Override
            public void delete() {
                MLog.e("删除");
                int size = adapter.getCount();
                for (int i = 0; i < size; i++) {
                    if (adapter.isChecked(i)) {
                        app.categoryDetailDAO.deleteInfo(bookInfoDBs.get(i).getTitle());
                    }
                }
                int choseSize = adapter.getCheckedItemCount();
                CategoryInfo categoryInfo = app.categoryDAO.queryCourseByTitle(category);
                if (!TextUtils.isEmpty(categoryInfo.getNums())) {
                    int nums = Integer.parseInt(categoryInfo.getNums());
                    categoryInfo.setNums((nums - choseSize) + "");
                    app.savaCate(categoryInfo);
                }
                bookInfoDBs = app.categoryDetailDAO.queryBookByCategory(category);
                rebuildList();
                adapter.setBookInfoList(bookInfoDBs);
            }

            @Override
            public void home() {
                activityUtil.jumpBackTo(MainActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        category = getIntent().getExtras().getString("title");
        boolean isHavaBook = getIntent().getExtras().getBoolean("isHaveBook");
        if (bookInfoDBs == null) {
            bookInfoDBs = new ArrayList<>();
        }
        if (isHavaBook) {
            bookInfoDBs = app.categoryDetailDAO.queryBookByCategory(category);
        } else {
            String result = getIntent().getExtras().getString("bookdata");
            BookTitleData bookTitleData = JSON.parseObject(result, BookTitleData.class);
            List<BookInfo> bookInfos = bookTitleData.getBooks();
            bookInfoDBs.clear();
            for (int i = 0, size = bookInfos.size(); i < size; i++) {
                BookInfoDB bookInfoDB = new BookInfoDB(bookInfos.get(i), category);
                bookInfoDBs.add(bookInfoDB);
            }
            if (bookTitleData != null && bookTitleData.getBooks() != null && bookTitleData.getBooks().size() > 0) {
                app.savaCate(new CategoryInfo(bookTitleData.getBooks().size() + "", category));
                MLog.e(category + "书本数量：" + bookTitleData.getBooks().size());
            }
            app.categoryDetailDAO.insertCategoryDetailFromNet(bookInfoDBs);
        }
        CategoryInfo categoryInfo = app.categoryDAO.queryCourseByTitle(category);
        if (bookInfoDBs != null) {
            categoryInfo.setNums(bookInfoDBs.size() + "");
            app.categoryDAO.update(categoryInfo);
        } else {
            categoryInfo.setNums("0");
            app.categoryDAO.update(categoryInfo);
        }
        adapter.setBookInfoList(bookInfoDBs);
//
//        List<BookInfoDB> bookInfoDBs = new ArrayList<>();
//        bookInfoDBs.clear();
//        for (int i = 0, size = bookInfos.size(); i < size; i++) {
//            bookInfoDBs.add(new BookInfoDB(bookInfos.get(i), title));
//        }
//        app.savaBookInfoDbs(bookInfoDBs);
//        tvTitle.setText(title);
        getSupportActionBar().setTitle(category);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean isHava;
        BookDBBean bookDBBean = app.bookDetailDAO.queryCourseByTitle(bookInfoDBs.get(position).getTitle());
        if (bookDBBean != null && !TextUtils.isEmpty(bookDBBean.getTitle())) {
            isHava = true;
        } else {
            isHava = false;
        }
        if (isHava) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("havaThisBook", true);
            bundle.putSerializable("bookInfo", bookDBBean);
            activityUtil.jumpTo(BookDetailActivity.class, bundle);
        } else {
            if (app.isNetworkAvailable()) {
                showTipsDialogs();
                Httpmanage.getInstance().getBookDetailData(bookInfoDBs.get(position).getBookid(), new Httpmanage.DataListener() {
                    @Override
                    public void onData(final String result) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dismissTipsDialogs();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("havaThisBook", false);
                                bundle.putString("fenlei", category);
                                bundle.putString("book", result);
                                activityUtil.jumpTo(BookDetailActivity.class, bundle);
                            }
                        });
                    }
                });
            } else {
                ToastUtils.show(mContext, "没有网络~");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activityUtil.jumpBackTo(MainActivity.class);
                return true;
            case R.id.menu_select_all:
                selectAll();
                return true;
            case R.id.menu_reset_list:
                rebuildList();
                return true;
        }
        return false;
    }


    private void selectAll() {
        for (int i = 0; i < adapter.getCount(); ++i) {
            adapter.setItemChecked(i, true);
        }
        adapter.notifyDataSetChanged();
    }

    private void rebuildList() {
        for (int i = 0; i < adapter.getCount(); ++i) {
            adapter.setItemChecked(i, false);
        }
        adapter.notifyDataSetChanged();
    }

}
