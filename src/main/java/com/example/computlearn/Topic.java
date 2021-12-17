package com.example.computlearn;

class Topic {
    //variables used for modules which appear in the list view
    private String id;
    private String name;
    private int img;

    //constructor which sets layout of how a module will be added
    public Topic(String id, String name, int img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }
    //getter and setter for variables
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

