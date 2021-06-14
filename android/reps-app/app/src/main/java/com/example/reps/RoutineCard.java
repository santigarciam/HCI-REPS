package com.example.reps;

import com.example.reps.retrofit.api.model.Routine;

public class RoutineCard {
    public String name;
    public String owner;
    public String description;
    public Integer id;
    public float rating = 0;

    public RoutineCard(String name, String owner,String description,Integer id) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.id = id;
    }

    public RoutineCard(Routine rut) {
        this.name = rut.getName();
        this.owner = rut.getUser().getUsername();
        this.description = rut.getDetail();
        this.id = rut.getId();
        this.rating = rut.getAverageRating();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public float getRating() {
        return rating;
    }

    public void setterRating(float rating) {
        this.rating = rating;
    }
}
