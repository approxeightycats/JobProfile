package com.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * temporary stand-in for what will be used to interact with whatever db
 */
public class DBInterface {

    public DBInterface() {

    }

    private List<String> readFile(String filename) {
        List<String> contents = new ArrayList<>();
        String path = "src/main/resources/info/" + filename + ".csv";
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                contents.add(scanner.nextLine());
            }
        }
        catch (IOException e){
            System.out.println("file does not exist");
        }
        return contents;
    }

    public static void addJob(Job job) {

    }

    public static void editJob(String jobID) {

    }

    public static void removeJob(String jobID) {

    }

    public static void addEquipment(Equipment equipment) {

    }

    public static void removeEquipment(Equipment equipment) {

    }

    public static int getOpenJobID() {
        int someID = 0;
        return someID + 1;
    }

    public static String getOpenClientID() {
        int someID = 0;
        return "C" + Integer.toString(someID);
    }

}
