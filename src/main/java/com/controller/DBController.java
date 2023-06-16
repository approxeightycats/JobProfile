package com.controller;

import com.model.Client;
import com.model.Equipment;
import com.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * temporary stand-in for what will be used to interact with whatever db
 */
public class DBController {

    private static volatile DBController instance;
    private static final String URL = "jdbc:sqlite:src/main/resources/info.db";

    private DBController() { }

    public static DBController getInstance() {
        DBController result = instance;
        if (result != null) {
            return result;
        }
        synchronized (DBController.class) {
            if (instance == null) {
                instance = new DBController();
            }
            return instance;
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

    public static void addClientName(Client client) {
        String sql = "INSERT INTO CLIENTS (FIRST_NAME, LAST_NAME) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTS";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Client c = new Client();
                c.setClientID(rs.getString("CLIENT_ID"));
                c.setFirstName(rs.getString("FIRST_NAME"));
                c.setLastName(rs.getString("LAST_NAME"));
                c.setOrg(rs.getString("ORGANIZATION"));
                c.setTitle(rs.getString("TITLE"));
                c.setEmail(rs.getString("EMAIL"));
                c.setPhone(rs.getString("PHONE"));
                c.setAddress(rs.getString("ADDRESS"));
                clients.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clients;
    }

    public static String getClientID(String first, String last) {
        String sql = "SELECT CLIENT_ID FROM CLIENTS WHERE FIRST_NAME = (?) AND LAST_NAME = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, first);
            ps.setString(2, last);
            ResultSet rs = ps.executeQuery();
            String s = rs.getString("CLIENT_ID");
            return s;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static Client getClient(String clientID) {
        String sql = "SELECT FROM * CLIENTS WHERE CLIENT_ID = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clientID);
            ResultSet rs = ps.executeQuery();

            Client c = new Client();
            c.setClientID(rs.getString("CLIENT_ID"));
            c.setFirstName(rs.getString("FIRST_NAME"));
            c.setLastName(rs.getString("LAST_NAME"));
            c.setTitle(rs.getString("TITLE"));
            c.setOrg(rs.getString("ORGANIZATION"));
            c.setEmail(rs.getString("EMAIL"));
            c.setPhone(rs.getString("PHONE"));
            c.setAddress(rs.getString("ADDRESS"));

            return c;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new Client();
    }


    public static void removeClient(String id) {
        String sql = "DELETE FROM CLIENTS WHERE CLIENT_ID = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setClientData(String field, String val, String id) {
        String sql = "UPDATE CLIENTS SET (?) = (?) WHERE CLIENT_ID = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, field.toUpperCase());
            ps.setString(2, val);
            ps.setString(3, id);
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

    public static void backup() {

    }

}
