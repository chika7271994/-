package com.example.kakeibo.activites.item;

public class IncomeListItems {

    private int income_id;
    private String income_category;
    private int income_price;
    private String imageUri;


    public int getIncome_id() {
        return income_id;
    }

    public void setIncome_id(int income_id) {
        this.income_id = income_id;
    }

    //----------------------------------------------------------------------------------

    public String getIncome_category(){
        return income_category;
    }

    public void setIncome_category(String income_category){ this.income_category = income_category; }

    //----------------------------------------------------------------------------------

    public int getIncome_price(){
        return income_price;
    }

    public void setIncome_price(int income_price) {
        this.income_price = income_price;
    }

    //-----------------------------------------------------------------------------------

    public String getImageUri() { return imageUri; }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
