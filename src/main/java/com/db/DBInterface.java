package com.db;

import com.model.Client;
import com.model.Equipment;
import com.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * temporary stand-in for what will be used to interact with whatever db
 */
public class DBInterface {

    private static final String URL = "jdbc:sqlite:src/main/resources/info/info.db";

    public DBInterface() {

    }

    public void viewTable() {
        String sql = "SELECT JOBID, NAME FROM JOBS";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("JOBID") +  "\t" +
                        rs.getString("NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connect() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(URL);
            System.out.println("Connection to DB has been established.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public void addJob(Job job) {
        String sql = "INSERT INTO JOBS(JOBID, CLIENT, NAME) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, job.getJobID());
                ps.setString(2, job.getClientID());
                ps.setString(3, job.getName());
                ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addClient(Client client) {
        String sql = "INSERT INTO CLIENTS (CODE, NAME, TITLE, EMAIL, PHONE, ADDRESS) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.getClientID());
            ps.setString(2, client.getName());
            ps.setString(3, client.getTitle());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getAddress());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addEquipment(Equipment equipment) {
        String sql = "INSERT INTO EQUIPMENT(DEPARTMENT, ITEM, \"REPLACEMENT COST\", \"DATE OF SERVICE \") VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, equipment.getDepartment());
            ps.setString(2, equipment.getName());
            ps.setInt(4, equipment.getReplacementCost());
            ps.setString(3, equipment.getDateOfService());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editJobStatus(int jobID, String status) {
        String sql = "UPDATE JOBS SET STATUS = (?) WHERE JOBID = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, jobID);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeJob(int jobID) {
        String sql = "DELETE FROM JOBS WHERE JOBID = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jobID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeEquipment(String name) {
        String sql = "DELETE FROM EQUIPMENT WHERE ITEM = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getOpenJobID() {
        int someID = 0;
        String sql = "SELECT JOBID FROM JOBS";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int v = Integer.parseInt(rs.getString("JOBID"));
                someID = Math.max(v, someID);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return someID + 1;
    }

    public static String getOpenClientID() {
        int someID = 0;
        String sql = "SElECT CODE FROM CLIENTS";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String c = rs.getString("CODE");
                int cVal = Integer.parseInt(c.substring(1));
                someID = Math.max(cVal, someID);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        someID++;
        int padSize = 6 - Integer.toString(someID).length();
        return "C" + String.format(Locale.US, "%0" + padSize + "d", someID);
    }

    public static void backup() {

    }

}
