package com.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {

    private static volatile ScreenController instance;
    private final HashMap<String, Pane> screenMap = new HashMap<>();
    private final Scene main;

    private ScreenController(Scene main) {
        this.main = main;
    }

    public static ScreenController getInstance(Scene scene) {
        ScreenController result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ScreenController.class) {
            if (instance == null) {
                instance = new ScreenController(scene);
            }
            return instance;
        }
    }

    protected void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        main.setRoot( screenMap.get(name) );
    }
}
