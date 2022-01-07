package sample.dao;

import javafx.beans.property.SimpleStringProperty;
import sample.Main;

import java.sql.*;
import java.util.Map;

public class GetInformation
{
    public static final int STUDENT = 1;

    // 从数据库中得到学生个人用户信息数据，
    // 而后将其保存到一个map中，
    // 根据该map，填充个人用户信息界面
    public static void getInformation(Map<String, SimpleStringProperty> map, int operation)
    {
        try
        {
            //        打包时将数据库置于jar的同一路径
            Connection connection = DriverManager.getConnection("jdbc:sqlite:library_manager.db");            Statement query = connection.createStatement();
            ResultSet data = null;
            if (operation == STUDENT)
            {
                data = query.executeQuery("select * from student where ID =" + Main.getID() + ";");
            }
            ResultSetMetaData resultSetMetaData = data.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++)
                map.put(resultSetMetaData.getColumnName(i), new SimpleStringProperty(data.getObject(i).toString()));
            connection.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
