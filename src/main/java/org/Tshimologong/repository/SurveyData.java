package org.Tshimologong.repository;

public class SurveyData {
    public String fullNames;
    public String email;
    public int age;
    public String contactNumber;
    public String favoriteFood;
    public int movies;
    public int radio;
    public int eatOut;
    public int watchTv;

    public SurveyData(String fullNames, String email, int age, String contactNumber, String favoriteFood, int movies, int radio, int eatOut, int watchTv) {
        this.fullNames = fullNames;
        this.email = email;
        this.age = age;
        this.contactNumber = contactNumber;
        this.favoriteFood = favoriteFood;
        this.movies = movies;
        this.radio = radio;
        this.eatOut = eatOut;
        this.watchTv = watchTv;
    }
}
