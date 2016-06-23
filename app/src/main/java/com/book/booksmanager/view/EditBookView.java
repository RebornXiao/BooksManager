package com.book.booksmanager.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.book.booksmanager.R;
import com.book.booksmanager.data.BookDBBean;
import com.book.booksmanager.utils.ToastUtils;

/**
 * Created by HP-PC on 2016/6/19.
 */
public class EditBookView extends RelativeLayout implements AdapterView.OnItemSelectedListener {

    EditText etBookTitle;
    EditText etBookZuozhe;
    EditText etBookChubanshe;
    Spinner spinnerCategory;
    EditText etBookJianjie;
    TextView tvSaveBook;
    private BookDBBean bookDBBean;
    private SavaBookListener listener;
    private Context context;
    private int position = 0;
    private String[] mItems;

    public void setBookDBBean(BookDBBean bookDBBean, SavaBookListener listener) {
        this.listener = listener;
        this.bookDBBean = bookDBBean;
        initData();
    }

    public EditBookView(Context context) {
        super(context);
        init(context);
    }

    public EditBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditBookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(final Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_edit_book, this);
        etBookTitle = (EditText) view.findViewById(R.id.et_book_title);
        etBookZuozhe = (EditText) view.findViewById(R.id.et_book_zuozhe);
        etBookChubanshe = (EditText) view.findViewById(R.id.et_book_chubanshe);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinner_category);
        etBookJianjie = (EditText) view.findViewById(R.id.et_book_jianjie);
        tvSaveBook = (TextView) view.findViewById(R.id.tv_save_book);
        tvSaveBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etBookTitle.getText().toString().trim();
                String zuozhe = etBookZuozhe.getText().toString().trim();
                String chubanshe = etBookChubanshe.getText().toString().trim();
                String jianjie = etBookJianjie.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.show(context, "书名不可为空");
                    return;
                }
                bookDBBean.setTitle(title);
                bookDBBean.setAuthor(zuozhe);
                bookDBBean.setPublisher(chubanshe);
                bookDBBean.setSummary(jianjie);
                if (mItems != null && mItems.length > position&&!TextUtils.isEmpty(mItems[position])) {
                    bookDBBean.setCategory(mItems[position]);
                }
                if (listener != null) {
                    listener.sava(bookDBBean);
                }
            }
        });

    }

    private void initData() {
        etBookTitle.setText(bookDBBean.getTitle());
        etBookZuozhe.setText(bookDBBean.getAuthor());
        etBookChubanshe.setText(bookDBBean.getPublisher());
        etBookJianjie.setText(bookDBBean.getSummary());
        mItems = bookDBBean.getTags().split(",");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, mItems);
        spinnerCategory.setAdapter(arrayAdapter);
        spinnerCategory.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        position = 0;
    }


    public interface SavaBookListener {
        void sava(BookDBBean bookDBBean);
    }
}
