package sample.dao;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import sample.Main;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FillTable
{
    public static final int BOOK = 1;

    public static final int MANAGER = 2;

    public static final int STUDENT = 3;

    public static final int BORROW = 4;

    // 该方法从数据库中得到数据，
    // 并将其填充到一个ObservableList<Map<String, SimpleStringProperty>>，
    // 进而根据该ObservableList，填充tableView
    public static void fillTable(ObservableList<Map<String, SimpleStringProperty>> observableList, int operation)
    {
        Platform.runLater(observableList::clear);
        try
        {
            //        打包时将数据库置于jar的同一路径
            Connection connection = DriverManager.getConnection("jdbc:sqlite" +
                    ":library_manager.db");
            Statement query = connection.createStatement();
            ResultSet data = null;
            if (operation == BOOK)
            {
                data = query.executeQuery("select * from book;");
            } else if (operation == MANAGER)
            {
                data = query.executeQuery("select * from manager;");
            } else if (operation == STUDENT)
            {
                data = query.executeQuery("select * from student;");
            } else if (operation == BORROW)
            {
                data = query.executeQuery("select" + "  main.book.ID," + "  main.book" +
                        ".name," + "  main.book.author," + "  main.book.press," + "  " +
                        "main.book.category," + "  30-(julianday('now')-julianday(main" +
                        ".borrow.borrow_time)) as dead_line\n" + "from main.book, main" +
                        ".borrow\n" + "where borrow.book_ID = book.ID and borrow" +
                        ".borrower_ID = " + Main.getID() + ";");
            }
            ResultSetMetaData resultSetMetaData = data.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (data.next())
            {
                Map<String, SimpleStringProperty> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++)
                {
                    rowData.put(resultSetMetaData.getColumnName(i),
                            new SimpleStringProperty(data.getObject(i).toString()));
                }
                Platform.runLater(() -> observableList.add(rowData));
            }
            connection.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
