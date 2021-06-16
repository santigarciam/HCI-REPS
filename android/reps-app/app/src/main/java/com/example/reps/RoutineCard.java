package com.example.reps;

import com.example.reps.retrofit.api.model.Routine;

import java.util.Objects;

public class RoutineCard {
    public String name;
    public String owner;
    public String description;
    public Integer id;
    public boolean isFavourite;
    private String difficulty;
    public float rating = 0;

    public RoutineCard(String name, String owner,String description,Integer id) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.id = id;
        this.isFavourite = false;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public RoutineCard(Routine rut) {
        this.name = rut.getName();
        this.owner = rut.getUser().getUsername();
        this.description = rut.getDetail();
        this.id = rut.getId();
        this.rating = (float) (rut.getAverageRating()/2);
        this.difficulty = rut.getDifficulty();
        this.isFavourite = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutineCard that = (RoutineCard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
