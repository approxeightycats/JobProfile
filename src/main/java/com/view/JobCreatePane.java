package com.view;

import com.controller.DBController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public class JobCreatePane {

    private BorderPane createJobPane;
    protected Button back;
    private final String name;

    protected JobCreatePane(Pane root) {
        createJobPane = new BorderPane();
        back = new Button("Back to main");
        this.name = "Untitled";
        createJobPane.setCenter(createMainVBox());
        BorderPane.setAlignment(createJobPane, Pos.CENTER);
    }

    protected VBox createMainVBox() {
        VBox jobInfoBoxes = new VBox(10);
        Label jobNumberLabel = new Label("Job #: " + (DBController.getLatestJobId()));
        Label jobNameLabel = new Label("Job name: " + name);
        Label jobStatusLabel = new Label("Job status:");
        Button cInfoLink = new Button("Client info");
        Button productionLink = new Button("Production");
        Button techInfoLink = new Button("Tech info");
        Button equipmentLink = new Button("Equipment");
        Button summaryLink = new Button("Summary");
        Button invoiceLink = new Button("Invoice");
        ArrayList<Button> jobCreatePaneButtons = new ArrayList<>(Arrays.asList(
                cInfoLink, productionLink, techInfoLink, equipmentLink, summaryLink, invoiceLink, back
        ));
        for (Button b : jobCreatePaneButtons) {
            b.setPrefWidth(100);
            b.setMinWidth(100);
        }
        jobInfoBoxes.getChildren().addAll(Arrays.asList(
                jobNumberLabel, jobNameLabel, jobStatusLabel, cInfoLink, productionLink,
                techInfoLink, equipmentLink, summaryLink, invoiceLink, back
        ));
        jobInfoBoxes.setAlignment(Pos.CENTER_LEFT);
        return jobInfoBoxes;
    }

    private void initHandlers(Pane root) {

    }

    public BorderPane getCreateJobPane() {
        return createJobPane;
    }

}
