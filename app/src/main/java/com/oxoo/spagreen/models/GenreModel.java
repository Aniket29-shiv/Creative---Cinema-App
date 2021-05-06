package com.oxoo.spagreen.models;


import java.util.ArrayList;
import java.util.List;

public class GenreModel {
    String name,id;
    List<CommonModels> list=new ArrayList<>();


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

    public List<CommonModels> getList() {
        return list;
    }

    public void setList(List<CommonModels> list) {
        this.list = list;
    }
}
