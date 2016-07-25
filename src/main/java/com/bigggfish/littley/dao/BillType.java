package com.bigggfish.littley.dao;

/**
 * Created by android on 2016/7/21.
 */
public class BillType {

    private int id;
    private String title;
    private int imageId;

    private BillType(int id, String title, int imageId){
        this.id = id;
        this.title = title;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
