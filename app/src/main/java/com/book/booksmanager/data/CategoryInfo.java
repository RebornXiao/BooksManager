package com.book.booksmanager.data;

public class CategoryInfo {

    private int id;
    private String nums;
    private String title;


    public CategoryInfo(String nums, String title) {
        this.nums = nums;
        this.title = title;
    }

    public CategoryInfo() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

