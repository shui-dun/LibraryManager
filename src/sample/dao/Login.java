package sample.dao;

import javafx.scene.control.Label;
import sample.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login
{
    // 根据得到的账号和密码，判断是否能登陆，
    // 若能，进入用户主界面，
    // 若不能，显示登陆失败的提示
    public static void login(String account, String password,
                             Label labelWrongPassword)
    {
        try
        {
            //        打包时将数据库置于jar的同一路径
            Connection connection = DriverManager.getConnection("jdbc:sqlite" +
                    ":library_manager.db");            //
            // 必须为每一个resultSet创建一个statement
            Statement statementStudent = connection.createStatement();
            Statement statementManager = connection.createStatement();
            ResultSet resultSetStudent = statementStudent.executeQuery(
                    "select password from student where ID=" + account + ";");
            ResultSet resultSetManager = statementManager.executeQuery(
                    "select password from manager where ID=" + account + ";");
            if (resultSetStudent.next() && resultSetStudent.getInt(1) == Integer.parseInt(password))
            {
                Main.showStage("StudentHomePage");
            } else if (resultSetManager.next() && resultSetManager.getInt(1) == Integer.parseInt(password))
            {
                Main.showStage("ManagerHomePage");
            } else
            {
                Main.promptFade(labelWrongPassword);
            }
            connection.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
