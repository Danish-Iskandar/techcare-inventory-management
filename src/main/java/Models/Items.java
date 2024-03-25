package Models;

import java.time.LocalDateTime;

public class Items {
    int ItemsID;
    String ItemName, ItemRemarks;
    Integer ItemQuantity;

    public Items(int ItemsID,String ItemName, Integer ItemQuantity, String ItemRemarks) {
        this.ItemsID = ItemsID;
        this.ItemName = ItemName;
        this.ItemQuantity =  ItemQuantity;
        this.ItemRemarks = ItemRemarks;

    }

    public int getItemsID() {
        return ItemsID;
    }

    public void setItemsID(int ItemsID) {
        this.ItemsID = ItemsID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {this.ItemName = ItemName;}

    public Integer getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int ItemQuantity) {
        this.ItemQuantity = ItemQuantity;
    }

    public String getItemRemarks() {
        return ItemRemarks;
    }

    public void setItemRemarks(String ItemRemarks) {
        this.ItemRemarks = ItemRemarks;
    }




}
