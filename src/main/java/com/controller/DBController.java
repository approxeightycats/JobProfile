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

    public static void addBlankJob() {
        String sql = "INSERT INTO JOBS DEFAULT VALUES ";
        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getLatestJobId() {
        String sql = "SELECT MAX(JOB_ID) FROM JOBS";
        int latest = 0;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            latest = rs.getInt("max(JOB_ID)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return latest;
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
//            ps.setString(6, client.getAddress());
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

    public static void editEquipmentItem(String value, String serial) {
        String sql = "UPDATE EQUIPMENT SET ITEM = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, serial);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editEquipmentDepartment(String value, String serial) {
        String sql = "UPDATE EQUIPMENT SET DEPARTMENT = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, serial);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editEquipmentReplacementCost(Integer value, String serial) {
        String sql = "UPDATE EQUIPMENT SET REPLACEMENT_COST = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, value);
            ps.setString(2, serial);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editEquipmentDoS(String value, String serial) {
        String sql = "UPDATE EQUIPMENT SET DATE_OF_SERVICE = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, serial);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void editEquipmentStatus(String value, String serial) {
        String sql = "UPDATE EQUIPMENT SET STATUS = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, serial);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editEquipmentRentalRate(Integer value, String serial) {
        String sql = "UPDATE EQUIPMENT SET RENTAL_RATE = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, value);
            ps.setString(2, serial);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editEquipmentSerial(String value, String serial) {
        String sql = "UPDATE EQUIPMENT SET SERIAL = (?) WHERE SERIAL = (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, serial);
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
                c.setAddressLine1(rs.getString("ADDRESS_LINE_1"));
                c.setAddressLine2(rs.getString("ADDRESS_LINE_2"));
                c.setAddressCity(rs.getString("ADDRESS_CITY"));
                c.setAddressState(rs.getString("ADDRESS_STATE"));
                c.setAddressZip(rs.getString("ADDRESS_ZIP"));
                c.setContactPrimary(rs.getString("CONTACT_0"));
                c.setContactSecondary(rs.getString("CONTACT_1"));
                c.setContactTertiary(rs.getString("CONTACT_2"));
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
                j.setJobName(rs.getString("JOB_NAME"));
                j.setClientId(rs.getString("CLIENT_ID"));
                j.setJobId(rs.getString("JOB_ID"));
                jobs.add(j);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobs;
    }

//    public static Equipment getEquipment(String serial) {
//        Equipment e = new Equipment();
//        String sql = "SELECT * FROM EQUIPMENT WHERE SERIAL = (?)";
//        try (Connection conn = connect();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, serial);
//            ResultSet rs = ps.executeQuery();
//            e.setItem(rs.getString("ITEM"));
//            e.setDepartment(rs.getString("DEPARTMENT"));
//            e.setReplacementCost(rs.getInt("REPLACEMENT_COST"));
//            e.setDateOfService(rs.getString("DATE_OF_SERVICE"));
//            e.setSerial(rs.getString("SERIAL"));
//            e.setStatus(rs.getString("STATUS"));
//            e.setRentalCost(rs.getInt("RENTAL_RATE"));
//        }
//        catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return e;
//    }

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

//    public static Client getClient(String clientID) {
//        String sql = "SELECT FROM * CLIENTS WHERE CLIENT_ID = (?)";
//        try (Connection conn = connect();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, clientID);
//            ResultSet rs = ps.executeQuery();
//
//            Client c = new Client();
//            c.setClientID(rs.getString("CLIENT_ID"));
//            c.setFirstName(rs.getString("FIRST_NAME"));
//            c.setLastName(rs.getString("LAST_NAME"));
//            c.setTitle(rs.getString("TITLE"));
//            c.setOrg(rs.getString("ORGANIZATION"));
//            c.setEmail(rs.getString("EMAIL"));
//            c.setPhone(rs.getString("PHONE"));
//            c.setAddress(rs.getString("ADDRESS"));
//
//            return c;
//        }
//        catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return new Client();
//    }


    public static void addClient(ArrayList<String> values) {
        String sql = "INSERT INTO CLIENTS " +
                "(FIRST_NAME, LAST_NAME, TITLE, EMAIL, PHONE, ORGANIZATION," +
                "CONTACT_0, CONTACT_1, CONTACT_2, ADDRESS_LINE_1, ADDRESS_LINE_2," +
                "ADDRESS_CITY, ADDRESS_STATE, ADDRESS_ZIP) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i < 15; i++) {
                ps.setString(i, values.get(i - 1));
            }
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

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

    private static void clientEditThing(String cid, String val, String sql) {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, val);
            ps.setString(2, cid);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editClientId(String cid, String val) {
        String sql = "UPDATE CLIENTS SET CLIENT_ID = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientNameFirst(String cid, String val) {
        String sql = "UPDATE CLIENTS SET FIRST_NAME = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientNameLast(String cid, String val) {
        String sql = "UPDATE CLIENTS SET LAST_NAME = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientTitle(String cid, String val) {
        String sql = "UPDATE CLIENTS SET TITLE = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientEmail(String cid, String val) {
        String sql = "UPDATE CLIENTS SET EMAIL = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientPhone(String cid, String val) {
        String sql = "UPDATE CLIENTS SET PHONE = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientContactPrimary(String cid, String val) {
        String sql = "UPDATE CLIENTS SET CONTACT_0 = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientContactSecondary(String cid, String val) {
        String sql = "UPDATE CLIENTS SET CONTACT_1 = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientContactTertiary(String cid, String val) {
        String sql = "UPDATE CLIENTS SET CONTACT_2 = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientAddressLine1(String cid, String val) {
        String sql = "UPDATE CLIENTS SET ADDRESS_LINE_1 = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientAddressLine2(String cid, String val) {
        String sql = "UPDATE CLIENTS SET ADDRESS_LINE_2 = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientAddressCity(String cid, String val) {
        String sql = "UPDATE CLIENTS SET ADDRESS_CITY = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientAddressState(String cid, String val) {
        String sql = "UPDATE CLIENTS SET ADDRESS_STATE = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientAddressZip(String cid, String val) {
        String sql = "UPDATE CLIENTS SET ADDRESS_ZIP = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
    }

    public static void editClientOrganization(String cid, String val) {
        String sql = "UPDATE CLIENTS SET ORGANIZATION = (?) WHERE CLIENT_ID = (?)";
        clientEditThing(cid, val, sql);
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
        String sql = "DELETE FROM JOBS WHERE JOB_ID = (?)";
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

    public static void addJobByName(String name) {
        String sql = "INSERT INTO JOBS (JOB_NAME) VALUES (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getJobId(String clientId, String name) {
        String sql = "SELECT JOB_ID FROM JOBS WHERE CLIENT_ID = (?) AND JOB_NAME = (?)";
        String jobId = "";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clientId);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            jobId = rs.getString("JOB_ID");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobId;
    }

    public static void backup() {

    }

}
