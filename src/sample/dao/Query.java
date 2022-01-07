package sample.dao;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Query
{
    public static final int STUDENT = 1;

    public static final int BOOK = 2;

    public static final int MANAGER = 3;

    // 根据用户输入的字符串，
    // 在数据库中得到相应的表，
    // 并据此更新可观察的列表
    public static void search(ObservableList<Map<String, SimpleStringProperty>> observableList, int operation, String string)
    {
        try
        {
            //        打包时将数据库置于jar的同一路径
            Connection connection = DriverManager.getConnection("jdbc:sqlite:library_manager.db");            Statement query = connection.createStatement();
            ResultSet data = null;
            string = string.trim();
            if (operation == BOOK)
            {
                data = query.executeQuery("select * from book where name like '%" + string + "%' or author like '%" + string + "%' or press like '%" + string + "%' or category like '%" + string + "%';");
            } else if (operation == STUDENT)
            {
                data = query.executeQuery("select * from student where last_name like '%" + string + "%' or first_name like '%" + string + "%' or gender like '%" + string + "%' or faculty like '%" + string + "%';");
            } else if (operation == MANAGER)
            {
                data = query.executeQuery("select * from manager where last_name like '%" + string + "%' or gender like '%" + string + "%' or first_name like '%" + string + "%' ;");
            }
            ResultSetMetaData resultSetMetaData = data.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            Platform.runLater(observableList::clear);
            while (data.next())
            {
                Map<String, SimpleStringProperty> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++)
                    rowData.put(resultSetMetaData.getColumnName(i), new SimpleStringProperty(data.getObject(i).toString()));
                Platform.runLater(() -> observableList.add(rowData));
            }
            connection.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
