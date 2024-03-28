package org.example.inventorymanagement;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import Models.Orders;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderListController implements Initializable {

    public static boolean setRefresh = false;

    public Button btnDashboard;
    @FXML
    private Label currentTime;
    public FontAwesomeIconView iconRefresh;

    @FXML
    private TableView<Orders> ordersTable;
    @FXML
    private TableColumn<Orders, String> idCol;
    @FXML
    private TableColumn<Orders, String> nameCol;
    @FXML
    private TableColumn<Orders, String> flavourCol;
    @FXML
    private TableColumn<Orders, String> toppingsCol;
    @FXML
    private TableColumn<Orders, String> statusCol;
    @FXML
    private TableColumn<Orders, String> datetimeCol;
    @FXML
    private TableColumn<Orders, String> editCol;

    String query = null;
    Connection connection = DBConnect.getConnect() ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Orders orders = null ;

    ObservableList<Orders> OrderList = FXCollections.observableArrayList();

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
    public void switchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 854, 480);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void refreshTable() {
        try {
            OrderList.clear();

            query = "SELECT * FROM `orderlist`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                OrderList.add(new Orders(
                        resultSet.getInt("OrderID"),
                        resultSet.getString("OrderName"),
                        resultSet.getString("OrderPhone"),
                        resultSet.getString("Flavour"),
                        resultSet.getString("Toppings"),
                        resultSet.getString("Status"),
                        resultSet.getTimestamp("DateTime").toLocalDateTime()));
                ordersTable.setItems(OrderList);
            }


        } catch (SQLException ex) {
            Logger.getLogger(OrderListController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void loadData(){
        refreshTable();


        idCol.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("OrderName"));
        flavourCol.setCellValueFactory(new PropertyValueFactory<>("Flavour"));
        toppingsCol.setCellValueFactory(new PropertyValueFactory<>("Toppings"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        datetimeCol.setCellValueFactory(new PropertyValueFactory<>("DateTime"));

        //add cell of button edit
        Callback<TableColumn<Orders, String>, TableCell<Orders, String>> cellFoctory = (TableColumn<Orders, String> param) -> {
            // make cell containing buttons
            final TableCell<Orders, String> cell = new TableCell<Orders, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);

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
                                    orders = ordersTable.getSelectionModel().getSelectedItem();
                                    query = "DELETE FROM `orderlist` WHERE OrderID  ="+orders.getOrderID();
                                    connection = DBConnect.getConnect();
                                    preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.execute();
                                    refreshTable();

                                } catch (SQLException ex) {
                                    Logger.getLogger(OrderListController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (alert.getResult() == ButtonType.CANCEL) {

                            }



                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                orders = ordersTable.getSelectionModel().getSelectedItem();
                                ordersTable.getSelectionModel().getSelectedItem();

                                query = "UPDATE `orderlist` SET "
                                        + "`Status`= ? WHERE OrderID = '" + orders.getOrderID() + "'";

                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, "Completed");
                                preparedStatement.execute();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            refreshTable();


                            /*FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/org/example/inventorymanagement/EDIT-ORDER.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(OrderListController.class.getName()).log(Level.SEVERE, null, ex);
                            }*

                            AddOrderController addOrderController = loader.getController();
                            addOrderController.setUpdate(true);
                            addOrderController.setTextField(orders.getOrderID(), orders.getOrderName(),
                                    orders.getOrderPhone(),orders.getFlavour(), orders.getToppings(), orders.getStatus());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();*/
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
        ordersTable.setItems(OrderList);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeNow();
        loadData();
    }
}

