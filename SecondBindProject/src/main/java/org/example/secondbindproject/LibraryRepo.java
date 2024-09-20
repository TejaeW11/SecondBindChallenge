package org.example.secondbindproject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class LibraryRepo {

    String dbName;
    String dbUser;
    String userPassword;
    String url;

    public LibraryRepo(){

        this.dbName = "SecondBindProj";
        this.dbUser = "postgres";
        this.userPassword = "$$admin468";
        this.url = "jdbc:postgresql://localhost:5432/" + dbName;

    }

    // Check return variable with a test
    private ResultSet executeSQL(String sql){

        Statement statement;
        ResultSet resultSet = null;

        try{
            Connection conn = DriverManager.getConnection(url, dbUser, userPassword);
            //Connection Indication
            System.out.println("Connected");
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            conn.close();



        }catch (SQLException e){
            System.out.println("Not Connected");
            e.printStackTrace();
        }

        return resultSet;
    }

//    String title, String author, String genre,
//    String date, String ISBN
    // Input should be a list of information [title, author, genre, publication date, ISBN]
    public void addBook(String[] book) throws NullPointerException, ParseException {

        String title ="";
        String author = "";
        String genre = "";
        String date = "";
//        Date date = null;
        String ISBN = "";

        // Validates info
        for (int i=0; i<book.length;i++){
            String info = book[i];
            if(info.isEmpty()){
                throw new NullPointerException("Information cannot be empty");
            }else{
                switch (i) {
                    case 0:
                        title = info;
                        break;
                    case 1:
                        author = info;
                        break;
                    case 2:
                        genre = info;
                        break;
                    case 3:
                        date = info;
//                        date = sqlDateFormat(info);
                        break;
                    case 4:
                        ISBN = info;
                        break;
                }
            }
        }
        String sql = "INSERT INTO inventory (title, author, genre, date, isbn)\n" +
                "VALUES ('"+title+"', '"+author+"', '"+genre+"', '"+date+"', '"+ISBN+"');";

//        String sql = "INSERT INTO inventory (title, author, genre, date, isbn) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement pstmt = DriverManager.getConnection(url, dbUser, userPassword).prepareStatement(sql)) {
//            pstmt.setString(1, title);
//            pstmt.setString(2, author);
//            pstmt.setString(3, genre);
//            pstmt.setString(4, String.valueOf(date));
//            pstmt.setString(5, ISBN);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            // Handle the SQL exception
//            e.printStackTrace();
//        }

        executeSQL(sql);
    }

    // Complete formatter
    // Method to validate date format for sql
    private Date sqlDateFormat(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse(date);
        Date sqlDate = new Date(utilDate.getTime());

        return sqlDate;
    }

}
