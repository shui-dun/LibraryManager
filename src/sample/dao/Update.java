package sample.dao;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class Update
{
    public static final int MANAGER = 1;

    public static final int STUDENT = 2;

    public static final int BOOK = 3;

    //    该方法将管理员更改好的信息保存到数据库
    public static void update(ObservableList<Map<String, SimpleStringProperty>> mapObservableList, int operation) throws SQLException
    {
        //        打包时将数据库置于jar的同一路径
        Connection connection = DriverManager.getConnection("jdbc:sqlite:library_manager.db");        if (operation == MANAGER)
        {
            PreparedStatement updateStatement = connection.prepareStatement("update manager\n" + "set last_name=?,first_name=?, gender=?,birth=?, password=?\n" + "where ID=?;");
            for (Map<String, SimpleStringProperty> map : mapObservableList)
            {
                updateStatement.setString(1, map.get("last_name").getValue());
                updateStatement.setString(2, map.get("first_name").getValue());
                updateStatement.setString(3, map.get("gender").getValue());
                updateStatement.setString(4, map.get("birth").getValue());
                updateStatement.setString(5, map.get("password").getValue());
                updateStatement.setString(6, map.get("ID").getValue());
                updateStatement.executeUpdate();
            }
        } else if (operation == BOOK)
        {
            PreparedStatement updateStatement = connection.prepareStatement("update book\n" + "set name=?,author=?, press=?,category=?, amount=?\n" + "where ID=?;");
            for (Map<String, SimpleStringProperty> map : mapObservableList)
            {
                updateStatement.setString(1, map.get("name").getValue());
                updateStatement.setString(2, map.get("author").getValue());
                updateStatement.setString(3, map.get("press").getValue());
                updateStatement.setString(4, map.get("category").getValue());
                updateStatement.setString(5, map.get("amount").getValue());
                updateStatement.setString(6, map.get("ID").getValue());
                updateStatement.executeUpdate();
            }
        } else if (operation == STUDENT)
        {
            PreparedStatement updateStatement = connection.prepareStatement("update student\n" + "set last_name=?,first_name=?, gender=?,class=?,faculty=?,password=?\n" + "where ID=?;");
            for (Map<String, SimpleStringProperty> map : mapObservableList)
            {
                updateStatement.setString(1, map.get("last_name").getValue());
                updateStatement.setString(2, map.get("first_name").getValue());
                updateStatement.setString(3, map.get("gender").getValue());
                updateStatement.setString(4, map.get("class").getValue());
                updateStatement.setString(5, map.get("faculty").getValue());
                updateStatement.setString(6, map.get("password").getValue());
                updateStatement.setString(7, map.get("ID").getValue());
                updateStatement.executeUpdate();
            }
        }
        connection.close();
    }
}
