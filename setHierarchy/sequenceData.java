package com.example.generateid;

public class sequenceData {
    private String name;
    private boolean isCloseClicked=false;

    public boolean isCloseClicked() {
        return isCloseClicked;
    }

    public void setCloseClicked(boolean closeClicked) {
        isCloseClicked = closeClicked;
    }

    public sequenceData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
