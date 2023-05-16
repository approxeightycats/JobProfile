package com.model;

import java.util.ArrayList;
import java.util.List;

public class Camera extends Equipment {

    private List<String> resolutions;
    private List<Float> framerates;

    public Camera(String name) {
        super(name);
        resolutions = new ArrayList<>();
        framerates = new ArrayList<>();
    }

    public void addResolution(String resolution) {
        resolutions.add(resolution);
    }

    public void addFramerate(Float framerate) {
        framerates.add(framerate);
    }

}
