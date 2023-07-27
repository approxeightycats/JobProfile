package com.controller;

import com.model.Client;
import com.model.Equipment;
import com.model.Job;

import java.sql.*;
import java.util.ArrayList;

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
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
        return c;
    }

    public void addJob(Job job) {
        String sql = "INSERT INTO JOBS(NAME, CLIENT_ID) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, job.getJobName());
            ps.setString(2, job.getClientId());
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

    public static void addClient(Client client) {
        String sql = "INSERT INTO CLIENTS (" +
                "FIRST_NAME, LAST_NAME, TITLE, EMAIL, PHONE, ADDRESS, ORGANIZATION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getTitle());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getAddress());
            ps.setString(7, client.getOrg());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addEquipment(Equipment equipment) {
        String sql = "INSERT INTO EQUIPMENT (" +
                "ITEM, DEPARTMENT, REPLACEMENT_COST, DATE_OF_SERVICE, SERIAL, STATUS, RENTAL_RATE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, equipment.getItem());
            ps.setString(2, equipment.getDepartment());
            ps.setInt(3, equipment.getReplacementCost());
            ps.setString(4, equipment.getDateOfService());
            ps.setString(5, equipment.getSerial());
            ps.setString(6, equipment.getStatus());
            ps.setInt(7, equipment.getRentalCost());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editEquipment(String field, String value, String serial) {
        String sql = "UPDATE EQUIPMENT SET (?) = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, field);
            ps.setString(2, value);
            ps.setString(3, serial);
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

    public static ArrayList<Job> getAllJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM JOBS";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Job j = new Job();
                j.setJobName(rs.getString("NAME"));
                j.setClientId(rs.getString("CLIENT_ID"));
                j.setJobId(rs.getString("JOB_ID"));
                jobs.add(j);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobs;
    }

    public static ArrayList<Equipment> getAllEquipment() {
        ArrayList<Equipment> equipment = new ArrayList<>();
        String sql = "SELECT * FROM EQUIPMENT";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipment e = new Equipment();
                e.setItem(rs.getString("ITEM"));
                e.setSerial(rs.getString("SERIAL"));
                e.setDepartment(rs.getString("DEPARTMENT"));
                e.setReplacementCost(rs.getInt("REPLACEMENT_COST"));
                e.setDateOfService(rs.getString("DATE_OF_SERVICE"));
                e.setStatus(rs.getString("STATUS"));
                e.setRentalCost(rs.getInt("RENTAL_RATE"));
                equipment.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return equipment;
    }

    public static String getClientID(String first, String last) {
        String sql = "SELECT CLIENT_ID FROM CLIENTS WHERE FIRST_NAME = (?) AND LAST_NAME = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, first);
            ps.setString(2, last);
            ResultSet rs = ps.executeQuery();
            return rs.getString("CLIENT_ID");
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

    public static void setClientData(String[] vals, String id) {
        String sql = "UPDATE CLIENTS SET " +
                "FIRST_NAME = (?), " +
                "LAST_NAME = (?), " +
                "TITLE = (?), " +
                "EMAIL = (?), " +
                "PHONE = (?), " +
                "ADDRESS = (?), " +
                "ORGANIZATION = (?) " +
                "WHERE CLIENT_ID = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vals[0]);
            ps.setString(2, vals[1]);
            ps.setString(3, vals[2]);
            ps.setString(4, vals[3]);
            ps.setString(5, vals[4]);
            ps.setString(6, vals[5]);
            ps.setString(7, vals[6]);
            ps.setString(8, id);
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

    public static void removeEquipment(String serial) {
        String sql = "DELETE FROM EQUIPMENT WHERE SERIAL = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serial);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void backup() {

    }

}
