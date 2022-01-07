package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.dao.SignUp;

import java.util.HashMap;
import java.util.Map;

public class ManagerSignUpPageController
{
    @FXML
    AnchorPane root;

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private TextField textLastName, textFirstName, textBirth, textID, textPassword;

    @FXML
    Label labelOK, labelLose;

    @FXML
    private Button buttonBack, buttonOK;

    @FXML
    private void initialize()
    {
        genderBox.getItems().addAll("保密", "男", "女");
        genderBox.setValue("保密");

        //        返回登录界面
        buttonBack.setOnAction(event ->
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

        buttonOK.setOnAction(event ->
        {
            Map<String, String> map = new HashMap<>();
            map.put("last_name", textLastName.getText());
            map.put("first_name", textFirstName.getText());
            map.put("gender", genderBox.getValue());
            map.put("birth", textBirth.getText());
            map.put("ID", textID.getText());
            map.put("password", textPassword.getText());
            new Thread(() ->
            {
                try
                {
                    SignUp.addNewUser(map, SignUp.MANAGER);
                    Platform.runLater(() -> Main.promptFade(labelOK));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Platform.runLater(() -> Main.promptFade(labelLose));
                }
            }).start();
        });

        //        设置快捷键
        root.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                buttonOK.fire();
        });
    }
}
