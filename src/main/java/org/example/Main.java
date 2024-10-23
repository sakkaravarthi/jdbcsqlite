package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

// connect to the SQlite database

    private static Connection connect() {
        String url = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connection to SQLite has been established");

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  conn;
    }

// create a new table
    public  static void createNewTable() {
        String sql = " CREATE TABLE IF NOT EXISTS students (\n "
                + "id integer PRIMARY KEY, \n"
                + " name text NOT NULL, \n"
                + " age integer \n"
                + ");";

        try (Connection conn = connect();
           Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // insert data into the table
    public  static void insert(String name, int age) {
        String sql = " INSERT INTO students (name, age) VALUES(?,?)";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // update a record/tuple/row
    public static void updateStudent(int id, String newName, int newAge) {
        String sql = "UPDATE students SET name = ? , age = ?  WHERE id = ?";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, newAge);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("An Existing student was updated successfully");
            } else {
                System.out.println(" No student with the provided id exists");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // delete a record/tuple/row

    public static void deleteStudent(int id) {
        String sql = "DELETE FROM students where id = ?";
        try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted= pstmt.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println(" the student with ID " + id + " has been deleted successfully");
            } else {
                System.out.println(" not student with that id provided exists");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // read all the data from the table
    public static void selectAll() {
        String sql = " SELECT id, name, age FROM students";
        try (Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getInt("age"));

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // sql query to alter the table and add a column

    public static void addColumn( String columnName, String columType) {
        String sql = "ALTER TABLE students add " + columnName + " " + columType;
        try(Connection conn = connect();
        Statement stmt = conn.createStatement()) {
         // execute the alter table query
         stmt.executeUpdate(sql);
            System.out.println("Column " + columnName + " has been added successfully");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // method to drop the table
    public  static void dropTable( String tableName) {
        String sql = " DROP TABLE if exists " + tableName;
        try(Connection conn = connect();
            Statement stmt = conn.createStatement()) {
            // execute the alter table query
            stmt.executeUpdate(sql);
            System.out.println("Table " + tableName + " has been deleted successfully");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
      createNewTable();
      insert("Alice", 22);
      insert("bob",35);
      selectAll();
      updateStudent(1, "Musk",33);
      selectAll();
      deleteStudent(1);
      selectAll();
      dropTable("students");
      selectAll();
    }
}