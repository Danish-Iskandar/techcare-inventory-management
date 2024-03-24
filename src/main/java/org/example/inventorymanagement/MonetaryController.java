package org.example.inventorymanagement;

import Models.Orders;
import Models.Statements;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonetaryController implements Initializable {
    public Button btnDashboard;
    public Button btnCashier;
    public Button btnOrderList;
    public Button btnInventory;
    public Button btnMoney;
    public Button btnAddStatement;

    @FXML
    private TableView<Statements> statementsTable;
    @FXML
    private TableColumn<Statements, String> idCol;
    @FXML
    private TableColumn<Statements, String> descCol;
    @FXML
    private TableColumn<Statements, String> outCol;
    @FXML
    private TableColumn<Statements, String> inCol;
    @FXML
    private TableColumn<Statements, String> remarksCol;
    @FXML
    private TableColumn<Statements, String> datetimeCol;


    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Statements statements = null ;

    ObservableList<Statements> StatementList = FXCollections.observableArrayList();
    public void switchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToCashier(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("CASHIER.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToOrderList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ORDER-LIST.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToInventory(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("INVENTORY.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToMonetary(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MONETARY.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void setBtnAddStatement(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("AddStatement.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    private void AddStatement(){

    }

    @FXML
    public void refreshTable() {
        try {
            StatementList.clear();

            query = "SELECT * FROM `statement`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                StatementList.add(new Statements(
                        resultSet.getInt("StatementID"),
                        resultSet.getString("StatementDescription"),
                        resultSet.getString("OutMoney"),
                        resultSet.getString("InMoney"),
                        resultSet.getString("StatementRemarks"),
                        resultSet.getTimestamp("DateTime").toLocalDateTime()));
                statementsTable.setItems(StatementList);

            }


        } catch (SQLException ex) {
            Logger.getLogger(MonetaryController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void loadData(){
        connection = DBConnect.getConnect();
        refreshTable();


        idCol.setCellValueFactory(new PropertyValueFactory<>("StatementID"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("StatementDescription"));
        outCol.setCellValueFactory(new PropertyValueFactory<>("OutMoney"));
        inCol.setCellValueFactory(new PropertyValueFactory<>("InMoney"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("StatementRemarks"));
        datetimeCol.setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        statementsTable.setItems(StatementList);


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }
}


