package Models;

public class ItemStateSingleton {
    private static final ItemStateSingleton INSTANCE = new ItemStateSingleton();

    private String itemState;

    // Private constructor to prevent instantiation from outside
    private ItemStateSingleton() {
        // Initialize the item state to some default value
        itemState = "DefaultState";
    }

    // Public static method to access the singleton instance
    public static ItemStateSingleton getInstance() {
        return INSTANCE;
    }

    // Getter and setter for the item state
    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }
}
