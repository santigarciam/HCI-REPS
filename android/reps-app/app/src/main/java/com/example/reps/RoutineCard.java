package com.example.reps;

public class RoutineCard {
    public String name;
    public String owner;
    public String description;

    public RoutineCard(String name, String owner,String description) {
        this.name = name;
        this.owner = owner;
        this.description = description;
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
}
