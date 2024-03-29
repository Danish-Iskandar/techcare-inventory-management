package org.example.inventorymanagement;

import Models.Orders;
import Models.Statements;
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

public class MonetaryController implements Initializable {
    public Button btnDashboard;
    public Button btnAddStatement;
    @FXML
    private Label currentTime;
    @FXML
    private TextField txtInMoney;
    @FXML
    private TextField txtOutMoney;
    public FontAwesomeIconView iconRefresh;

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
    Connection connection = DBConnect.getConnect() ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Statements statements = null ;
    Double inMoney = 0.00;
    Double outMoney = 0.00;

    ObservableList<Statements> StatementList = FXCollections.observableArrayList();
    public void switchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("DASHBOARD.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 854, 480);
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
    @FXML
    public void refreshTable() {
        try {
            StatementList.clear();
            inMoney= 0.00;
            outMoney= 0.00;

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

                Statements statements = new Statements(
                        resultSet.getInt("StatementID"),
                        resultSet.getString("StatementDescription"),
                        resultSet.getString("OutMoney"),
                        resultSet.getString("InMoney"),
                        resultSet.getString("StatementRemarks"),
                        resultSet.getTimestamp("DateTime").toLocalDateTime());
                inMoney += Double.valueOf(statements.getInMoney());
                outMoney += Double.valueOf(statements.getOutMoney());

            }


        } catch (SQLException ex) {
            Logger.getLogger(MonetaryController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void loadData(){
        refreshTable();


        idCol.setCellValueFactory(new PropertyValueFactory<>("StatementID"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("StatementDescription"));
        outCol.setCellValueFactory(new PropertyValueFactory<>("OutMoney"));
        inCol.setCellValueFactory(new PropertyValueFactory<>("InMoney"));
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("StatementRemarks"));
        datetimeCol.setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        statementsTable.setItems(StatementList);
        String formattedInMoney = String.format("%.2f", inMoney);
        String formattedOutMoney = String.format("%.2f", outMoney);
        txtInMoney.setText(String.valueOf(formattedInMoney));
        txtOutMoney.setText(String.valueOf(formattedOutMoney));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeNow();
        loadData();

    }
}


