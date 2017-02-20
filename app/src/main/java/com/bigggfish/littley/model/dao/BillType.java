package com.bigggfish.littley.model.dao;

/**
 * Created by android on 2016/7/21.
 */
public class BillType {

    private int id; //类别id
    private String title; //类别名称
    private int imageId; //类别图片资源id

    public BillType(){

    }
    public BillType(int id, String title, int imageId){
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
