package com.example.reps;

import java.util.List;

public class RoutineSection {
    private String sectionTitle;
    private List<RoutineCard> listOfRoutines;

    public RoutineSection(String sectionTitle, List<RoutineCard> listOfRoutines) {
        this.sectionTitle = sectionTitle;
        this.listOfRoutines = listOfRoutines;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<RoutineCard> getListOfRoutines() {
        return listOfRoutines;
    }

    public void setListOfRoutines(List<RoutineCard> listOfRoutines) {
        this.listOfRoutines = listOfRoutines;
    }
}
