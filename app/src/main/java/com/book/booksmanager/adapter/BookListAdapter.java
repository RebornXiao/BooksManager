package com.book.booksmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.book.booksmanager.R;
import com.book.booksmanager.data.CategoryInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BookListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<CategoryInfo> data;

    public void setData(List<CategoryInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public BookListAdapter(Context mContext) {
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_message, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (data != null && data.size() > position) {
            holder.tvMsgTitle.setText(data.get(position).getTitle());
            holder.tvMsgNums.setText(data.get(position).getNums()+"");
        }

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_msg_nums)
        TextView tvMsgNums;
        @Bind(R.id.tv_msg_title)
        TextView tvMsgTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
