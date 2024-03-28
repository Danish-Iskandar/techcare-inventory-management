package org.example.inventorymanagement;

import Models.ItemStateSingleton;
import Models.Items;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryController implements Initializable {
    // Get the singleton instance
    ItemStateSingleton itemStateSingleton = ItemStateSingleton.getInstance();

    // Get the current item state
    String currentState = itemStateSingleton.getItemState();

    public Button btnDashboard;
    public Button btnAddUtensil;
    public Button btnAddIngredient;
    @FXML
    private Label currentTime;
    @FXML
    private ChoiceBox<String> chbInventory;
    private String[] inventoryChoice = {"Utensil", "Ingredient"};

    @FXML
    private TableView<Items> itemsTable;
    @FXML
    private TableColumn<Items, String> idCol;
    @FXML
    private TableColumn<Items, String> itemCol;
    @FXML
    private TableColumn<Items, String> quantityCol;
    @FXML
    private TableColumn<Items, String> remarksCol;
    @FXML
    private TableColumn<Items, String> editCol;

    String query = null;
    Connection connection = DBConnect.getConnect() ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Items items = null ;

    ObservableList<Items> ItemList = FXCollections.observableArrayList();

    private void timeNow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            try {
                while (true) { // Infinite loop
                    Thread.sleep(1000);
                    final String timeNow = timeFormat.format(new java.util.Date());
                    Platform.runLater(() -> {
                        currentTime.setText(timeNow);
                    });
                }
            } catch (InterruptedException e) {
                // Thread interrupted, do nothing
            }
        });
        thread.start(); // Start the thread
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeNow();
        chbInventory.getItems().addAll(inventoryChoice);
        chbInventory.setOnAction(this::getInventoryChoice);
        chbInventory.setValue("Utensil");
        itemStateSingleton.setItemState("Utensil");
        loadItem();
    }
    public void getInventoryChoice (ActionEvent event) {
        String invChoice = chbInventory.getValue();
        //Code for the ChoiceBox
        if (Objects.equals(invChoice, "Utensil")) {
            System.out.println(invChoice);
            itemStateSingleton.setItemState("Utensil");
            btnAddIngredient.setVisible(false);
            btnAddUtensil.setVisible(true);
            loadItem();

        } else if (Objects.equals(invChoice, "Ingredient")){
            System.out.println(invChoice);
            itemStateSingleton.setItemState("Ingredient");
            btnAddIngredient.setLayoutX(99); btnAddIngredient.setLayoutY(97);
            btnAddUtensil.setVisible(false);
            btnAddIngredient.setVisible(true);
            loadItem();
        } else {
            System.out.println("DEYYYYYY");
        }
    }


    public void refreshTable(){
        try {
            ItemList.clear();
            if (chbInventory.getValue()== "Utensil"){
                query = "SELECT * FROM `utensillist`";
            }else{
                query = "SELECT * FROM `ingredientlist`";
            }

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            String properID, properName, properQuantity, properRemarks;

            if (chbInventory.getValue()== "Utensil"){
                properID = "UtensilID";
                properName= "UtensilName";
                properQuantity= "UtensilQuantity";
                properRemarks= "UtensilRemarks";
            }else{
                properID = "IngredientID";
                properName= "IngredientName";
                properQuantity= "IngredientQuantity";
                properRemarks= "IngredientRemarks";
            }

            while (resultSet.next()){
                ItemList.add(new Items(
                        resultSet.getInt(properID),
                        resultSet.getString(properName),
                        resultSet.getInt(properQuantity),
                        resultSet.getString(properRemarks)));
                itemsTable.setItems(ItemList);

            }


        } catch (SQLException ex) {
            Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void loadItem(){
        refreshTable();


        idCol.setCellValueFactory(new PropertyValueFactory<>("ItemsID"));
        itemCol.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("ItemRemarks"));

        //add cell of button edit
        Callback<TableColumn<Items, String>, TableCell<Items, String>> cellFoctory = (TableColumn<Items, String> param) -> {
            // make cell containing buttons
            final TableCell<Items, String> cell = new TableCell<Items, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Are you sure?");
                            alert.setHeaderText("This action cannot be undone.");
                            alert.setContentText("Do you want to proceed?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                try {
                                    items = itemsTable.getSelectionModel().getSelectedItem();
                                    if (chbInventory.getValue()== "Utensil"){
                                        query = "DELETE FROM `utensillist` WHERE UtensilID  ="+items.getItemsID();
                                    }else{
                                        query = "DELETE FROM `ingredientlist` WHERE IngredientID  ="+items.getItemsID();
                                    }


                                    preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.execute();
                                    refreshTable();

                                } catch (SQLException ex) {
                                    Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (alert.getResult() == ButtonType.CANCEL) {

                            }



                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            items = itemsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/org/example/inventorymanagement/EDIT-ITEM.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            EditItemController editItemController = loader.getController();
                            editItemController.setUpdate(true);
                            editItemController.setTextField(items.getItemsID(), items.getItemName(),
                                    items.getItemQuantity(),items.getItemRemarks());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        itemsTable.setItems(ItemList);


    }



    public void switchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 854, 480);
        stage.setScene(scene);
        stage.show();
    }
    public void openAddUtensil(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ADD_UTENSIL.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void openAddIngredient(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ADD_INGREDIENT.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
