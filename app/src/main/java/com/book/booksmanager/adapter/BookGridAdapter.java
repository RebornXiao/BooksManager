package com.book.booksmanager.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.book.booksmanager.R;
import com.book.booksmanager.data.BookInfoDB;
import com.manuelpeinado.multichoiceadapter.MultiChoiceBaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookGridAdapter extends MultiChoiceBaseAdapter {

    private List<BookInfoDB> bookInfoList;

    private Context mContext;

    private BookManageListenr listenr;

    public void setListenr(BookManageListenr listenr) {
        this.listenr = listenr;
    }

    public BookGridAdapter(Bundle savedInstanceState, Context mContext) {
        super(savedInstanceState);
        this.mContext = mContext;
    }

    public void setBookInfoList(List<BookInfoDB> bookInfoList) {
        this.bookInfoList = bookInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (bookInfoList != null && bookInfoList.size() > 0) {
            return bookInfoList.size();
        }
        return 0;
    }

    // 返回每个位置对应的view对象。
    @Override
    public View getViewImpl(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext,
                R.layout.list_book_item, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_home_item);
        LinearLayout ll_book_list_all = (LinearLayout) view.findViewById(R.id.ll_book_list_all);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_home_item);
        if (bookInfoList != null && bookInfoList.size() > position) {
            if (isChecked(position)) {
                ll_book_list_all.setBackgroundResource(R.color.register_un);
            } else {
                ll_book_list_all.setBackgroundResource(R.color.white);
            }
            tv_name.setText(bookInfoList.get(position).getTitle());
            Picasso.with(mContext).load(bookInfoList.get(position).getImage()).into(iv_icon);
            tv_name.setTextColor(0xff2d5e66);
        }
        return view;
    }

    @Override
    public Object getItem(int position) {
        return bookInfoList;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.my_action_mode, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_discard:
                if(listenr!=null){
                    listenr.delete();
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        return false;
    }

    public interface BookManageListenr{
        void delete();

        void home();
    }
}
