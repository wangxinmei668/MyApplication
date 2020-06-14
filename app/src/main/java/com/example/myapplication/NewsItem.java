package com.example.myapplication;

public class NewsItem {
    private int id;
    private String curNews;
    private String curlink;

    public NewsItem(){
        super();
        curNews="";
        curlink="";
    }

    public NewsItem(String curNews,String curlink){
        super();
        this.curNews=curNews;
        this.curlink=curlink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurNews() {
        return curNews;
    }

    public void setCurNews(String curNews) {
        this.curNews = curNews;
    }

    public String getCurlink() {
        return curlink;
    }

    public void setCurlink(String curRate) {
        this.curlink = curlink;
    }
}
