package sample.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;

public class SignUp
{
    public static final int MANAGER = 1;

    public static final int STUDENT = 2;

    public static final int MODIFY_STUDENT = 3;

    //    注册，或更新学生信息
    public static void addNewUser(Map<String, String> map, int operation) throws Exception
    {
        //        打包时将数据库置于jar的同一路径
        Connection connection = DriverManager.getConnection("jdbc:sqlite:library_manager.db");        if (operation == MANAGER)
        {
            if (!(map.get("ID").isEmpty() | map.get("last_name").isEmpty() | map.get("first_name").isEmpty() | map.get("gender").isEmpty() | map.get("birth").isEmpty() | map.get("password").isEmpty()))
            {
                PreparedStatement update = connection.prepareStatement("insert into manager values (?,?,?,?,?,?);");
                update.setString(1, map.get("ID"));
                update.setString(2, map.get("last_name"));
                update.setString(3, map.get("first_name"));
                update.setString(4, map.get("gender"));
                update.setString(5, map.get("birth"));
                update.setString(6, map.get("password"));
                update.executeUpdate();
            } else
                throw new IOException("字段不能为空");
        } else if (operation == STUDENT)
        {
            if (!(map.get("ID").isEmpty() | map.get("last_name").isEmpty() | map.get("first_name").isEmpty() | map.get("gender").isEmpty() | map.get("class").isEmpty() | map.get("faculty").isEmpty() | map.get("password").isEmpty()))
            {
                PreparedStatement update = connection.prepareStatement("insert into student values (?,?,?,?,?,?,?);");
                update.setString(1, map.get("ID"));
                update.setString(2, map.get("last_name"));
                update.setString(3, map.get("first_name"));
                update.setString(4, map.get("gender"));
                update.setString(5, map.get("class"));
                update.setString(6, map.get("faculty"));
                update.setString(7, map.get("password"));
                update.executeUpdate();
            } else
                throw new IOException("字段不能为空");
        } else if (operation == MODIFY_STUDENT)
        {
            if (!(map.get("ID").isEmpty() | map.get("last_name").isEmpty() | map.get("first_name").isEmpty() | map.get("gender").isEmpty() | map.get("class").isEmpty() | map.get("faculty").isEmpty() | map.get("password").isEmpty()))
            {
                PreparedStatement update = connection.prepareStatement("update student set ID=?,last_name=?,first_name=?,gender=?,class=?,faculty=?,password=? where ID=?;");
                update.setString(1, map.get("ID"));
                update.setString(2, map.get("last_name"));
                update.setString(3, map.get("first_name"));
                update.setString(4, map.get("gender"));
                update.setString(5, map.get("class"));
                update.setString(6, map.get("faculty"));
                update.setString(7, map.get("password"));
                update.setString(8, map.get("ID"));
                update.executeUpdate();
            } else
                throw new IOException("字段不能为空");
        }
        connection.close();
    }
}
