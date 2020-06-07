package com.greymat9er.shutup;

import android.net.Uri;

public class ShutUpMessages {

    private String text, name;
    private String photoUri;

    public ShutUpMessages(){
    }

    public ShutUpMessages(String text, String name, String photoUri) {
        this.text = text;
        this.name = name;
        this.photoUri = photoUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
