package sample.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import sample.Main;
import sample.dao.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StudentHomePageController
{
    @FXML
    Button buttonLogout; // 退出登录的按钮

    @FXML
    Button buttonModifyOK, buttonSearchBook, buttonReturn, buttonBorrow;

    @FXML
    Button buttonRefresh;  // 刷新还书界面的按钮

    @FXML
    Label labelReturnSuccess, labelReturnLose, labelBorrowSuccess, labelBorrowLose, labelBorrowBookNotSelected, labelReturnBookNotSelected;

    @FXML
    TextField textSearchBook, textLastName, textFirstName, textID, textClass, textFaculty, textPassword;

    @FXML
    ComboBox<String> genderBox;

    @FXML
    Label labelModifyOK, labelModifyLose;

    @FXML
    TableView<Map<String, SimpleStringProperty>> tableBorrow, tableReturn;

    private ObservableList<Map<String, SimpleStringProperty>> borrowList, returnList;

    private String currentSelectedBorrowBookID = "";
    private String currentSelectedReturnBookID = "";

    @FXML
    void initialize()
    {
        //        退出登录事件
        buttonLogout.setOnAction(event ->
        {
            try
            {
                Main.showStage("LoginPage");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        createBookTable();
        createReturnTable();
        fillInInformation();

        //        借书事件
        tableBorrow.getSelectionModel().selectedItemProperty().addListener(observable ->
        {
            try
            {
                currentSelectedBorrowBookID = tableBorrow.getSelectionModel().getSelectedItem().get("ID").getValue();
            }
            catch (NullPointerException e) {} // 忽略空指针异常
        });

        buttonBorrow.setOnAction(event ->
        {
            if (!currentSelectedBorrowBookID.isEmpty())
            {
                new Thread(() ->
                {
                    try
                    {
                        Borrow.borrowBook(currentSelectedBorrowBookID);
                        Platform.runLater(() -> Main.promptFade(labelBorrowSuccess));
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                        Platform.runLater(() -> Main.promptFade(labelBorrowLose));
                    }
                }).start();
            } else
            {
                Main.promptFade(labelBorrowBookNotSelected);
            }
        });

        //        还书事件
        tableReturn.getSelectionModel().selectedItemProperty().addListener(observable ->
        {
            try
            {
                currentSelectedReturnBookID = tableReturn.getSelectionModel().getSelectedItem().get("ID").getValue();
            }
            catch (NullPointerException e) {} // 忽略空指针异常
        });

        buttonReturn.setOnAction(event ->
        {
            if (!currentSelectedReturnBookID.isEmpty())
            {
                new Thread(() ->
                {
                    try
                    {
                        Return.returnBook(currentSelectedReturnBookID);
                        Platform.runLater(() -> Main.promptFade(labelReturnSuccess));
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                        Platform.runLater(() -> Main.promptFade(labelReturnLose));
                    }
                }).start();
            } else
            {
                Main.promptFade(labelReturnBookNotSelected);
            }
        });

        //        刷新还书列表事件
        buttonRefresh.setOnAction(event ->
        {
            new Thread(() -> FillTable.fillTable(returnList, FillTable.BORROW)).start();
        });

        //        搜索借书列表事件
        buttonSearchBook.setOnAction(event ->
        {
            String string = textSearchBook.getText();
            new Thread(() -> Query.search(borrowList, Query.BOOK, string)).start();
        });

        textSearchBook.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                buttonSearchBook.fire();
        });

        buttonModifyOK.setOnAction(event ->
        {
            Map<String, String> map = new HashMap<>();
            map.put("last_name", textLastName.getText());
            map.put("first_name", textFirstName.getText());
            map.put("gender", genderBox.getValue());
            map.put("class", textClass.getText());
            map.put("faculty", textFaculty.getText());
            map.put("ID", textID.getText());
            map.put("password", textPassword.getText());
            new Thread(() ->
            {
                try
                {
                    SignUp.addNewUser(map, SignUp.MODIFY_STUDENT);
                    Platform.runLater(() -> Main.promptFade(labelModifyOK));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Platform.runLater(() -> Main.promptFade(labelModifyLose));
                }
            }).start();
        });


    }

    //    创建借阅书籍的tableView
    private void createBookTable()
    {
        borrowList = FXCollections.observableArrayList();
        new Thread(() -> FillTable.fillTable(borrowList, FillTable.BOOK)).start();
        tableBorrow.setItems(borrowList);
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBorrowID = new TableColumn<>("ID");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBorrowName = new TableColumn<>("书名");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBorrowAuthor = new TableColumn<>("作者");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBorrowPress = new TableColumn<>("出版社");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBorrowCategory = new TableColumn<>("类别");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBorrowAmount = new TableColumn<>("库存量");

        tableColumnBorrowID.setCellValueFactory(param -> param.getValue().get("ID"));
        tableColumnBorrowName.setCellValueFactory(param -> param.getValue().get("name"));
        tableColumnBorrowAuthor.setCellValueFactory(param -> param.getValue().get("author"));
        tableColumnBorrowPress.setCellValueFactory(param -> param.getValue().get("press"));
        tableColumnBorrowCategory.setCellValueFactory(param -> param.getValue().get("category"));
        tableColumnBorrowAmount.setCellValueFactory(param -> param.getValue().get("amount"));

        tableColumnBorrowID.setMinWidth(180);
        tableColumnBorrowName.setMinWidth(200);
        tableColumnBorrowAuthor.setMinWidth(180);
        tableColumnBorrowPress.setMinWidth(180);
        tableColumnBorrowCategory.setMinWidth(180);
        tableColumnBorrowAmount.setMinWidth(180);

        tableBorrow.getColumns().add(tableColumnBorrowID);
        tableBorrow.getColumns().add(tableColumnBorrowName);
        tableBorrow.getColumns().add(tableColumnBorrowAuthor);
        tableBorrow.getColumns().add(tableColumnBorrowPress);
        tableBorrow.getColumns().add(tableColumnBorrowCategory);
        tableBorrow.getColumns().add(tableColumnBorrowAmount);

    }

    //    创建归还书籍的tableView
    private void createReturnTable()
    {
        returnList = FXCollections.observableArrayList();
        new Thread(() -> FillTable.fillTable(returnList, FillTable.BORROW)).start();
        tableReturn.setItems(returnList);
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnReturnID = new TableColumn<>("ID");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnReturnName = new TableColumn<>("书名");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnReturnAuthor = new TableColumn<>("作者");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnReturnPress = new TableColumn<>("出版社");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnReturnCategory = new TableColumn<>("类别");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnReturnDeadLine = new TableColumn<>("还书期限");

        tableColumnReturnID.setCellValueFactory(param -> param.getValue().get("ID"));
        tableColumnReturnName.setCellValueFactory(param -> param.getValue().get("name"));
        tableColumnReturnAuthor.setCellValueFactory(param -> param.getValue().get("author"));
        tableColumnReturnPress.setCellValueFactory(param -> param.getValue().get("press"));
        tableColumnReturnCategory.setCellValueFactory(param -> param.getValue().get("category"));
        tableColumnReturnDeadLine.setCellValueFactory(param ->
        {
            String originalDate = param.getValue().get("dead_line").getValue();
            return new SimpleStringProperty(Integer.toString((int) Math.ceil(Double.parseDouble(originalDate))));
        });

        tableColumnReturnID.setMinWidth(180);
        tableColumnReturnName.setMinWidth(200);
        tableColumnReturnAuthor.setMinWidth(180);
        tableColumnReturnPress.setMinWidth(180);
        tableColumnReturnCategory.setMinWidth(180);
        tableColumnReturnDeadLine.setMinWidth(180);

        tableReturn.getColumns().add(tableColumnReturnID);
        tableReturn.getColumns().add(tableColumnReturnName);
        tableReturn.getColumns().add(tableColumnReturnAuthor);
        tableReturn.getColumns().add(tableColumnReturnPress);
        tableReturn.getColumns().add(tableColumnReturnCategory);
        tableReturn.getColumns().add(tableColumnReturnDeadLine);
    }

    //    填充个人信息界面
    private void fillInInformation()
    {
        Map<String, SimpleStringProperty> map = new HashMap<>();
        GetInformation.getInformation(map, Query.STUDENT);
        textLastName.setText(map.get("last_name").getValue());
        textFirstName.setText(map.get("first_name").getValue());
        textClass.setText(map.get("class").getValue());
        textFaculty.setText(map.get("faculty").getValue());
        textID.setText(map.get("ID").getValue());
        textPassword.setText(map.get("password").getValue());

        genderBox.getItems().addAll("保密", "男", "女");
        genderBox.setValue(map.get("gender").getValue());
    }
}
