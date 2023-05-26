package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Camera extends Equipment {

    private List<String> resolutions;
    private List<Float> framerates;

    public Camera(String dept, String name, Float replacementCost, LocalDate dateOfService) {
        super(dept, name, replacementCost, dateOfService);
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
