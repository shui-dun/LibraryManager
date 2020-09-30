package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.dao.Login;

public class LoginController
{
    @FXML
    Label promptLabel, labelWrongPassword;

    @FXML
    ComboBox<String> signUpBox;

    @FXML
    Button buttonLogin;

    @FXML
    TextField accountText, passwordText;

    @FXML
    AnchorPane root;

    @FXML
    void initialize()
    {
        ObservableList<String> observableList= FXCollections.observableArrayList();
        observableList.addAll("注册", "职工", "学生");
        signUpBox.setItems(observableList);
        signUpBox.setValue("注册");

        // 由登录界面进入注册界面
        signUpBox.setOnAction(event ->
        {
            try
            {
                if (signUpBox.getValue() == "学生")
                    Main.showStage("StudentSignUpPage");
                else if (signUpBox.getValue() == "职工")
                    Main.showStage("ManagerSignUpPage");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        //        检查用户输入，只让用户输入数字
        accountText.setOnKeyPressed(event ->
        {
            if (event.getCode().isLetterKey())
                Main.promptFade(promptLabel);
        });
        passwordText.setOnKeyPressed(event ->
        {
            if (event.getCode().isLetterKey())
                Main.promptFade(promptLabel);
        });

        //        登录事件
        buttonLogin.setOnAction(event ->
        {
            String account = accountText.getText();
            String password = passwordText.getText();
            Main.setID(account);
            Login.login(account, password, labelWrongPassword);
        });

        //        设置快捷键
        root.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                buttonLogin.fire();
        });
    }
}
