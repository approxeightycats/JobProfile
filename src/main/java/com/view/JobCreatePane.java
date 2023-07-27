package com.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public class JobCreatePane {

    private final BorderPane createJobPane;
    protected Button back;

    protected JobCreatePane(Pane root) {
        createJobPane = new BorderPane();
        initOptions(createJobPane);
    }

    private void initOptions(BorderPane bp) {
        VBox jobInfoBoxes = new VBox(10);
        Label jobNumberLabel = new Label("Job #: ");
        Label jobNameLabel = new Label("Job name:");
        Label jobStatusLabel = new Label("Job status:");
        Button cInfoLink = new Button("Client info");
        Button productionLink = new Button("Production");
        Button techInfoLink = new Button("Tech info");
        Button equipmentLink = new Button("Equipment");
        Button summaryLink = new Button("Summary");
        Button invoiceLink = new Button("Invoice");
        back = new Button("Back to main");
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
        bp.setCenter(jobInfoBoxes);
        jobInfoBoxes.setAlignment(Pos.CENTER_LEFT);
        BorderPane.setAlignment(bp, Pos.CENTER);
    }

    private void initHandlers() {

    }

    public BorderPane getCreateJobPane() {
        return createJobPane;
    }

}
