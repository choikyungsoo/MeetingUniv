package com.example.meetinguniv.main;

public class ChattingDataItem {
    private String content;
    private String name;
    private int viewType;

    public ChattingDataItem(String content, String name ,int viewType) {
        this.content = content;
        this.viewType = viewType;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public int getViewType() {
        return viewType;
    }
}
