package org.example.secondbindproject;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {
    private Connection conn = null;

    // Inital DB connection to SQLite database (Found within project folder)
    public DBConnect(){
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:secondbinddb.db");
            System.out.println("Connected!!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("NOTTT Connected!!");
            e.printStackTrace();
        }
    }

    // Method to add a book into the database (Use on submission page)
    // No null fields
    public void addBook(String title, String author, String genre,
                        String date, Double isbn) throws NullPointerException, ParseException {

        String sql = "INSERT INTO inventory (title, author, genre, date, isbn) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
            pstmt.setString(4, date);
            pstmt.setDouble(5, isbn);

            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Method to get all columns and rows from the sql database at once
    // (Used for tableview/book list)
    public List<Book> getData() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM inventory";

        try (Statement stmt = this.conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("date"),
                        rs.getDouble("isbn")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }


}
