package sample.dao;

import sample.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//根据书籍ID，进行借书操作，并更新书籍数目
public class Borrow
{
    public static void borrowBook(String bookID) throws SQLException
    {
        //        打包时将数据库置于jar的同一路径
        Connection connection = DriverManager.getConnection("jdbc:sqlite" +
                ":library_manager.db");
        PreparedStatement borrowStatement = connection.prepareStatement("insert into " +
                "borrow (book_ID, borrower_ID)\n" + "values (?,?)" + ";");
        borrowStatement.setString(1, bookID);
        borrowStatement.setString(2, Main.getID());
        borrowStatement.executeUpdate();

        PreparedStatement updateAmountStatement = connection.prepareStatement("update " +
                "book\n" + "set " + "amount=amount-1\n" + "where ID=?;");
        updateAmountStatement.setString(1, bookID);
        updateAmountStatement.executeUpdate();
    }
}
