package com.book.booksmanager.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.book.booksmanager.R;
import com.book.booksmanager.adapter.BookListAdapter;
import com.book.booksmanager.data.BookInfoDB;
import com.book.booksmanager.data.CategoryInfo;
import com.book.booksmanager.net.Httpmanage;
import com.book.booksmanager.utils.MLog;
import com.book.booksmanager.utils.ScreenUtils;
import com.book.booksmanager.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.lv_book)
    SwipeMenuListView lvBook;
    @Bind(R.id.et_add_category)
    EditText etAddCategory;
    @Bind(R.id.tv_add_ok)
    TextView tvAddOk;
    private BookListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("图书管理");
        if (adapter == null) {
            adapter = new BookListAdapter(mContext);
        }
        lvBook.setAdapter(adapter);
        initMenuListView();

    }

    /**
     * 侧滑事件
     */
    private void initMenuListView() {
        //创建一个SwipeMenuCreator供ListView使用
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                //创建一个侧滑菜单
                SwipeMenuItem delItem = new SwipeMenuItem(getApplicationContext());
                //给该侧滑菜单设置背景
                delItem.setBackground(new ColorDrawable(Color.rgb(1, 187, 87)));
                //设置宽度
                delItem.setWidth(ScreenUtils.dp2px(mContext, 80));
                //设置图片
                delItem.setIcon(R.mipmap.icon_delete);
                delItem.setTitle("删除");
                //加入到侧滑菜单中
                menu.addMenuItem(delItem);
            }
        };
        lvBook.setMenuCreator(creator);
        //侧滑菜单的相应事件
        lvBook.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {//position从0开始
                switch (index) {
                    case 0://第二个添加的菜单的响应时间(删除)
                        if (!TextUtils.isEmpty(app.getCategoryInfos().get(position).getNums()) &&
                                Integer.parseInt(app.getCategoryInfos().get(position).getNums()) == 0) {
                            app.deleteCategory(app.getCategoryInfos().get(position).getTitle());
                            adapter.setData(app.getCategoryInfos());
                        } else {
                            ToastUtils.show(mContext, "对不起，删除失败，类别图书数量不为空！");
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        lvBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showTipsDialogs();
                List<BookInfoDB> bookInfoDBs = app.categoryDetailDAO.queryBookByCategory(app.getCategoryInfos().get(position).getTitle());
                if (bookInfoDBs != null && bookInfoDBs.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", app.getCategoryInfos().get(position).getTitle());
//                    bundle.put("bookdata", result);
                    bundle.putBoolean("isHaveBook", true);
                    dismissTipsDialogs();
                    activityUtil.jumpTo(BookListActivity.class, bundle);

                } else {
                    if (app.isNetworkAvailable()) {
                        Httpmanage.getInstance().getBookListData(app.getCategoryInfos().get(position).getTitle(), new Httpmanage.DataListener() {
                            @Override
                            public void onData(final String result) {
                                MLog.e("result:" + result);
                                        dismissTipsDialogs();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("title", app.getCategoryInfos().get(position).getTitle());
                                        bundle.putString("bookdata", result);
                                        bundle.putBoolean("isHaveBook", false);
                                        activityUtil.jumpTo(BookListActivity.class, bundle);
                            }
                        });
                    } else {
                        dismissTipsDialogs();
                        ToastUtils.show(mContext, "没有网络~");
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.setData(app.getCategoryInfos());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                activityUtil.jumpTo(SearchActivity.class);
                return true;
            case R.id.about:
                activityUtil.jumpTo(AboutActivity.class);
                return true;
        }
        return false;
    }

    @OnClick(R.id.tv_add_ok)
    void addBook() {
        String book = etAddCategory.getText().toString().trim();
        if (!TextUtils.isEmpty(book)) {
            app.savaCate(new CategoryInfo("0", book));
            adapter.setData(app.getCategoryInfos());
            ToastUtils.show(mContext, "添加成功！");
            etAddCategory.setText("");
        } else {
            ToastUtils.show(mContext, "请不要留空或者输入空格！");
            return;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            exitapplication();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
