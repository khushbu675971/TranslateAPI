package com.app.translate;


class TranslateResponse {
    private int statusCode;
    private String text;

    public void setValue(int statusCode, String text) {
    	this.statusCode = statusCode;
        this.text = text;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getText() {
        return text;
    }
}