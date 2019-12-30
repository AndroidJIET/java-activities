package com.example.jiet_mainactivity;


public class Search {
    public String Title;
    public String Description;
    public String year;
    public String Cell;

    public String getYear() {
        return year;
    }

    public Search() {
    }



    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCell() {
        return Cell;
    }

    public void setCell(String cell) {
        Cell = cell;
    }


    public void setYear(String year) {
        year = year;
    }

    public Search(String cell, String title, String description, String year) {
        Cell = cell;
        year = year;
        Title = title;
        Description = description;
    }
}
