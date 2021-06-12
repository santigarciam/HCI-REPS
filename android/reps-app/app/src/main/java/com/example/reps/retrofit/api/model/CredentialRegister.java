package com.example.reps.retrofit.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CredentialRegister extends Credentials{
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthdate")
    @Expose
    private Integer birthdate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("metadata")
    @Expose
    private String metada;


    public CredentialRegister(){
        super();
    }


    public CredentialRegister(String username, String password, String firstName, String lastName, String gender, Integer birthdate, String email, String phone, String avatarUrl, String metada) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender.toLowerCase();
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.metada = metada;
    }

}
