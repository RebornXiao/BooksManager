package com.book.booksmanager.data;

public class MessageInfo {

    private int nums;
    private String title;


    public MessageInfo(int nums, String title) {
        this.nums = nums;
        this.title = title;
    }

    public MessageInfo() {
        super();
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

