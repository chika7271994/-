package com.example.kakeibo.activites.item;

public class SpendingListItems {
    private int spending_id;
    private String spending_category;
    private int spending_price;
    private String imageUri;


    public int getSpending_id() {
        return spending_id;
    }

    public void setSpending_id(int spending_id) {
        this.spending_id = spending_id;
    }

    public String getSpending_category(){
        return spending_category;
    }

    public void setSpending_category(String spending_category){
        this.spending_category = spending_category;
    }

    public int getSpending_price(){
        return spending_price;
    }

    public void setSpending_price(int spending_price) {
        this.spending_price = spending_price;
    }

    public String getImageUri() { return imageUri; }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
