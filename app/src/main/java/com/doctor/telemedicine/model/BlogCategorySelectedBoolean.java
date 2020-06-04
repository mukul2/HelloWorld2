package com.doctor.telemedicine.model;

public class BlogCategorySelectedBoolean {
    boolean isSelected;
    BlogCategoryNameID blogCategoryNameID;

    public BlogCategorySelectedBoolean(boolean isSelected, BlogCategoryNameID blogCategoryNameID) {
        this.isSelected = isSelected;
        this.blogCategoryNameID = blogCategoryNameID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BlogCategoryNameID getBlogCategoryNameID() {
        return blogCategoryNameID;
    }

    public void setBlogCategoryNameID(BlogCategoryNameID blogCategoryNameID) {
        this.blogCategoryNameID = blogCategoryNameID;
    }
}
