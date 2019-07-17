package com.example.pojochat.Model;

public class ChatRoom {


    String id;
    String name;
    String desc;
    String date;

    public ChatRoom() {
    }

    public ChatRoom(String name, String desc, String date) {
        this.name = name;
        this.desc = desc;
        this.date = date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}