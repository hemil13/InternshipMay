package com.example.internshipmay;


public class SubcategoryList {

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getSubcategoryid() {
        return subcategoryid;
    }

    public void setSubcategoryid(int subcategoryid) {
        this.subcategoryid = subcategoryid;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int categoryid, subcategoryid, image;
    String name;
}
