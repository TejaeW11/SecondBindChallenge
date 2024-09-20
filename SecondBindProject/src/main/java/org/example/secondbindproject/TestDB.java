package org.example.secondbindproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;

public class TestDB {


    public static void main(String[] args) throws ParseException {

        DBConnect conn = new DBConnect();

        String title = "Harry Potter and the Prisoner of Azkaban";
        String author = "J.K Rowling";
        String genre = "Fantasy";
        String date = "1999-07-08";
        Double isbn = 9780439136358.;

        conn.addBook(title, author, genre, date, isbn);



    }
}
