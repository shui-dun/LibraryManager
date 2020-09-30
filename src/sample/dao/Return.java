package sample.dao;

import sample.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Return
{
    // 这是还书操作，
    // 根据书籍ID和借阅者ID，进行还书操作，
    // 并更新书籍库存量
    public static void returnBook(String bookID) throws SQLException
    {
        //        打包时将数据库置于jar的同一路径
        Connection connection = DriverManager.getConnection("jdbc:sqlite:library_manager.db");
        PreparedStatement returnStatement = connection.prepareStatement("delete from borrow where book_ID=? and borrower_ID=?;");
        returnStatement.setString(1, bookID);
        returnStatement.setString(2, Main.getID());
        returnStatement.executeUpdate();

        PreparedStatement updateAmountStatement = connection.prepareStatement("update book\n" + "set amount=amount+1\n" + "where ID=?;");
        updateAmountStatement.setString(1, bookID);
        updateAmountStatement.executeUpdate();
    }
}
