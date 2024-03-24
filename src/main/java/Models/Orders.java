package Models;


import java.time.LocalDateTime;

public class Orders {

    int OrderID;
    String OrderName, OrderPhone;
    String Flavour, Toppings, Status;
    LocalDateTime DateTime;

    public Orders(int OrderID, String OrderName, String OrderPhone, String Flavour, String Toppings, String Status, LocalDateTime DateTime) {
        this.OrderID = OrderID;
        this.OrderName = OrderName;
        this.OrderPhone= OrderPhone;
        this.Flavour = Flavour;
        this.Toppings = Toppings;
        this.Status = Status;
        this.DateTime= DateTime;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String OrderName) {
        this.OrderName = OrderName;
    }
    public String getOrderPhone() {
        return OrderPhone;
    }

    public void setOrderPhone(String OrderPhone) {
        this.OrderPhone = OrderPhone;
    }

    public String getFlavour() {
        return Flavour;
    }

    public void setFlavour(String Flavour) {
        this.Flavour = Flavour;
    }

    public String getToppings() {
        return Toppings;
    }

    public void setToppings(String Toppings) {
        this.Toppings = Toppings;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public LocalDateTime getDateTime() {
        return DateTime;
    }

    public void setDateTime(LocalDateTime DateTime) {
        this.DateTime = DateTime;
    }




}
