package com.example.okno_wyszukiwanie_kat_nazwy.Model;

public class Country {

    private String countryName;
    private String countryImage;

    public Country(String countryName, String countryImage) {
        this.countryName = countryName;
        this.countryImage = countryImage;
    }

    //Get methods

    public String getCountryName() {
        return countryName;
    }

    public String getCountryImage() {return countryImage;}

    //Set methods

    public void setCountryName(String categoryName) {
        this.countryName = categoryName;
    }

    public void setCountryImage(String countryImage) {this.countryImage = countryImage;}

}