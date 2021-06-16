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
        if(listOfRoutines.size() <5){
            return listOfRoutines;
        }
        return listOfRoutines.subList(0,5);
    }

    public void setListOfRoutines(List<RoutineCard> listOfRoutines) {
        this.listOfRoutines = listOfRoutines;
    }
}
