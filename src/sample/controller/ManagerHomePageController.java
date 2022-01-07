package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import sample.Main;
import sample.dao.Update;
import sample.dao.FillTable;
import sample.dao.Query;

import java.util.Map;


public class ManagerHomePageController
{
    @FXML
    Button buttonLogout;

    @FXML
    Button buttonModifyBook, buttonModifyStudent, buttonModifyManager;

    @FXML
    Button buttonSearchBook, buttonSearchStudent, buttonSearchManager;

    @FXML
    TextField textSearchStudent, textSearchBook, textSearchManager;

    @FXML
    TableView<Map<String, SimpleStringProperty>> tableStudent, tableManager, tableBook;

    @FXML
    Label labelModifyStudentSuccess, labelModifyManagerSuccess, labelModifyBookSuccess;

    @FXML
    Label labelModifyStudentLose, labelModifyManagerLose, labelModifyBookLose;

    //    存储被修改的职工/书籍/学生的新信息
    private ObservableList<Map<String, SimpleStringProperty>> listChangedManager, listChangedBook, listChangedStudent;
    private Map<String, SimpleStringProperty> mapChangedManager, mapChangedBook, mapChangedStudent;
    private ObservableList<Map<String, SimpleStringProperty>> studentList, managerList, bookList;

    @FXML
    void initialize()
    {
        //        退出登录
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


        createStudentTable();
        createManagerTable();
        createBookTable();
        //根据用户输入，搜索书籍信息
        buttonSearchBook.setOnAction(event ->
        {
            String string = textSearchBook.getText();
            new Thread(() -> Query.search(bookList, Query.BOOK, string)).start();
        });
        //根据用户输入，搜索职工信息
        buttonSearchManager.setOnAction(event ->
        {
            String string = textSearchManager.getText();
            new Thread(() -> Query.search(managerList, Query.MANAGER, string)).start();
        });
        //根据用户输入，搜索学生信息
        buttonSearchStudent.setOnAction(event ->
        {
            String string = textSearchStudent.getText();
            new Thread(() -> Query.search(studentList, Query.STUDENT, string)).start();
        });

        textSearchStudent.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                buttonSearchStudent.fire();
        });

        textSearchManager.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                buttonSearchManager.fire();
        });

        textSearchBook.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                buttonSearchBook.fire();
        });
    }

    //    创建学生的tableView
    //    感觉Java真的好啰嗦啊
    private void createStudentTable()
    {
        studentList = FXCollections.observableArrayList();
        new Thread(() -> FillTable.fillTable(studentList, FillTable.STUDENT)).start();
        tableStudent.setItems(studentList);
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnStudentID = new TableColumn<>("学号");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnStudentLastName = new TableColumn<>("姓");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnStudentFirstName = new TableColumn<>("名");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnStudentGender = new TableColumn<>("性别");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnStudentClass = new TableColumn<>("班级");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnStudentFaculty = new TableColumn<>("学院");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnStudentPassword = new TableColumn<>("密码");

        tableColumnStudentID.setCellValueFactory(param -> param.getValue().get("ID"));
        tableColumnStudentLastName.setCellValueFactory(param -> param.getValue().get("last_name"));
        tableColumnStudentFirstName.setCellValueFactory(param -> param.getValue().get("first_name"));
        tableColumnStudentGender.setCellValueFactory(param -> param.getValue().get("gender"));
        tableColumnStudentClass.setCellValueFactory(param -> param.getValue().get("class"));
        tableColumnStudentFaculty.setCellValueFactory(param -> param.getValue().get("faculty"));
        tableColumnStudentPassword.setCellValueFactory(param -> param.getValue().get("password"));

        tableColumnStudentID.setMinWidth(160);
        tableColumnStudentLastName.setMinWidth(160);
        tableColumnStudentFirstName.setMinWidth(160);
        tableColumnStudentGender.setMinWidth(160);
        tableColumnStudentClass.setMinWidth(160);
        tableColumnStudentFaculty.setMinWidth(160);
        tableColumnStudentPassword.setMinWidth(160);

        tableStudent.getColumns().add(tableColumnStudentID);
        tableStudent.getColumns().add(tableColumnStudentLastName);
        tableStudent.getColumns().add(tableColumnStudentFirstName);
        tableStudent.getColumns().add(tableColumnStudentGender);
        tableStudent.getColumns().add(tableColumnStudentClass);
        tableStudent.getColumns().add(tableColumnStudentFaculty);
        tableStudent.getColumns().add(tableColumnStudentPassword);

        tableColumnStudentLastName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnStudentFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnStudentGender.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnStudentClass.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnStudentFaculty.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnStudentPassword.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    //    创建职工的tableView
    private void createManagerTable()
    {
        managerList = FXCollections.observableArrayList();
        new Thread(() -> FillTable.fillTable(managerList, FillTable.MANAGER)).start();
        tableManager.setItems(managerList);
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnManagerID = new TableColumn<>("学号");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnManagerLastName = new TableColumn<>("姓");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnManagerFirstName = new TableColumn<>("名");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnManagerGender = new TableColumn<>("性别");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnManagerBirth = new TableColumn<>("出生日期");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnManagerPassword = new TableColumn<>("密码");

        tableColumnManagerID.setCellValueFactory(param -> param.getValue().get("ID"));
        tableColumnManagerLastName.setCellValueFactory(param -> param.getValue().get("last_name"));
        tableColumnManagerFirstName.setCellValueFactory(param -> param.getValue().get("first_name"));
        tableColumnManagerGender.setCellValueFactory(param -> param.getValue().get("gender"));
        tableColumnManagerBirth.setCellValueFactory(param -> param.getValue().get("birth"));
        tableColumnManagerPassword.setCellValueFactory(param -> param.getValue().get("password"));

        tableColumnManagerID.setMinWidth(200);
        tableColumnManagerLastName.setMinWidth(200);
        tableColumnManagerFirstName.setMinWidth(200);
        tableColumnManagerGender.setMinWidth(200);
        tableColumnManagerBirth.setMinWidth(200);
        tableColumnManagerPassword.setMinWidth(200);

        tableManager.getColumns().add(tableColumnManagerID);
        tableManager.getColumns().add(tableColumnManagerLastName);
        tableManager.getColumns().add(tableColumnManagerFirstName);
        tableManager.getColumns().add(tableColumnManagerGender);
        tableManager.getColumns().add(tableColumnManagerBirth);
        tableManager.getColumns().add(tableColumnManagerPassword);

        tableColumnManagerLastName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnManagerFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnManagerGender.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnManagerBirth.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnManagerPassword.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    //    创建书籍的tableView
    private void createBookTable()
    {
        bookList = FXCollections.observableArrayList();
        new Thread(() -> FillTable.fillTable(bookList, FillTable.BOOK)).start();
        tableBook.setItems(bookList);
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBookID = new TableColumn<>("ID");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBookName = new TableColumn<>("书名");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBookAuthor = new TableColumn<>("作者");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBookPress = new TableColumn<>("出版社");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBookCategory = new TableColumn<>("类别");
        TableColumn<Map<String, SimpleStringProperty>, String> tableColumnBookAmount = new TableColumn<>("库存量");

        tableColumnBookID.setCellValueFactory(param -> param.getValue().get("ID"));
        tableColumnBookName.setCellValueFactory(param -> param.getValue().get("name"));
        tableColumnBookAuthor.setCellValueFactory(param -> param.getValue().get("author"));
        tableColumnBookPress.setCellValueFactory(param -> param.getValue().get("press"));
        tableColumnBookCategory.setCellValueFactory(param -> param.getValue().get("category"));
        tableColumnBookAmount.setCellValueFactory(param -> param.getValue().get("amount"));

        tableColumnBookID.setMinWidth(180);
        tableColumnBookName.setMinWidth(200);
        tableColumnBookAuthor.setMinWidth(180);
        tableColumnBookPress.setMinWidth(180);
        tableColumnBookCategory.setMinWidth(180);
        tableColumnBookAmount.setMinWidth(180);

        tableBook.getColumns().add(tableColumnBookID);
        tableBook.getColumns().add(tableColumnBookName);
        tableBook.getColumns().add(tableColumnBookAuthor);
        tableBook.getColumns().add(tableColumnBookPress);
        tableBook.getColumns().add(tableColumnBookCategory);
        tableBook.getColumns().add(tableColumnBookAmount);

        tableColumnBookName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnBookAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnBookPress.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnBookCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnBookAmount.setCellFactory(TextFieldTableCell.forTableColumn());


        //    职工资料修改事件
        listChangedManager = FXCollections.observableArrayList();
        tableManager.getSelectionModel().selectedItemProperty().addListener(observable ->
        {
            mapChangedManager = tableManager.getSelectionModel().getSelectedItem();
            listChangedManager.add(mapChangedManager);
        });
        buttonModifyManager.setOnAction(event ->
        {
            try
            {
                Update.update(listChangedManager, Update.MANAGER);
                Main.promptFade(labelModifyManagerSuccess);
            }
            catch (Exception e)
            {
                Main.promptFade(labelModifyManagerLose);
                e.printStackTrace();
            }
            listChangedManager.clear();
        });

        //    学生资料修改事件
        listChangedStudent = FXCollections.observableArrayList();
        tableStudent.getSelectionModel().selectedItemProperty().addListener(observable ->
        {
            mapChangedStudent = tableStudent.getSelectionModel().getSelectedItem();
            listChangedStudent.add(mapChangedStudent);
        });
        buttonModifyStudent.setOnAction(event ->
        {
            try
            {
                Update.update(listChangedStudent, Update.STUDENT);
                Main.promptFade(labelModifyStudentSuccess);
            }
            catch (Exception e)
            {
                Main.promptFade(labelModifyStudentLose);
                e.printStackTrace();
            }
            listChangedStudent.clear();
        });

        //    书籍资料修改事件
        listChangedBook = FXCollections.observableArrayList();
        tableBook.getSelectionModel().selectedItemProperty().addListener(observable ->
        {
            mapChangedBook = tableBook.getSelectionModel().getSelectedItem();
            listChangedBook.add(mapChangedBook);
        });
        buttonModifyBook.setOnAction(event ->
        {
            try
            {
                Update.update(listChangedBook, Update.BOOK);
                Main.promptFade(labelModifyBookSuccess);
            }
            catch (Exception e)
            {
                Main.promptFade(labelModifyBookLose);
                e.printStackTrace();
            }
            listChangedBook.clear();
        });
    }

}
