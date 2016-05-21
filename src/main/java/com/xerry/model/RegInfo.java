package com.xerry.model;

import java.util.Date;

/**
 * Created by wwp on 2016/5/21.
 */
public class RegInfo {

    private String board;
    private String title;
    private String author;
    private String url;
    private String mail;
    private Date lastUpdateTime;


    public RegInfo(String board, String title, String author, String mail) {
        this.board = board;
        this.title = title;
        this.author = author;
        this.mail = mail;
    }

    public String getKey() {
        return board + title + author;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
