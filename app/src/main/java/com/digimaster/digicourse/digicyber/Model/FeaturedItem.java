package com.digimaster.digicourse.digicyber.Model;

/**
 * Created by carios on 19/02/18.
 */

public class FeaturedItem {

    public int type;
    public String headerText, bottomText, imgUrl, title, writer;

    public FeaturedItem(){

    }

    public FeaturedItem(int type, String headerText, String bottomText, String imgUrl, String title, String writer) {
        this.type = type;
        this.headerText = headerText;
        this.bottomText = bottomText;
        this.imgUrl = imgUrl;
        this.writer = writer;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public String getHeaderText() {
        return headerText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }
}
