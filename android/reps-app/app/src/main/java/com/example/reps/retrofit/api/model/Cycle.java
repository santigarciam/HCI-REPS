package com.example.reps.retrofit.api.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cycle {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("repetitions")
    @Expose
    private int repetitions;
    @SerializedName("metadata")
    @Expose
    private Object metadata;

    private PagedList<CycleExercise> cycleExercises = null;

    public Cycle() {
    }

    public Cycle(int id, String name, String detail, String type, int order, int repetitions, Object metadata) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
        this.repetitions = repetitions;
        this.metadata = metadata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public void setCycleExercises (PagedList<CycleExercise> exercises){
        this.cycleExercises = exercises;
    }

    public PagedList<CycleExercise> getCycleExercises(){
        return cycleExercises;
    }

}