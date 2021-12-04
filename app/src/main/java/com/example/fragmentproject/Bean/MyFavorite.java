package com.example.fragmentproject.Bean;

public class MyFavorite {
    private String context;
    private String title;

    public MyFavorite() {

    }

    public MyFavorite(String title, String context) {

        this.context = context;
        this.title = title;

    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MyFavorite{" +
                "context='" + context + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
