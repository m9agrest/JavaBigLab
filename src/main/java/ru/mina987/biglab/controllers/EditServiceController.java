package ru.mina987.biglab.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.mina987.biglab.domains.Service;

import java.util.ResourceBundle;

public class EditServiceController {
    private Service service;
    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }
    private ResourceBundle bundle;
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    public boolean isAction() {
        return action;
    }
    private boolean action=false;
    private Stage editStage;
    public void setService(Service service) {
        this.service = service;
        tName.setText(service.getName());
        tDuration.setText(String.valueOf(service.getDuration()));
        tPrice.setText(String.valueOf(service.getPrice()));
        if(service.getDescription()!=null)
            tDescription.setText(service.getDescription());
    }
    @FXML
    private TextArea tDescription;
    @FXML
    private TextField tDuration;
    @FXML
    private TextField tName;
    @FXML
    private TextField tPrice;
    @FXML
    void onCancel(ActionEvent event) {
        editStage.close();
    }
    private void Error(String text){
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(bundle.getString("error"));
        alert.setContentText(null);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
    @FXML
    void onOK(ActionEvent event) {
        if(tName.getText()==null || tName.getText().trim().isEmpty())
            Error(bundle.getString("servicenamefieldempty"));
        else if (tDuration.getText()==null ||tDuration.getText().trim().isEmpty() ||  Integer.parseInt(tDuration.getText()) <= 0)
            Error(bundle.getString("durationincorrect"));
        else if (tPrice.getText()==null ||tPrice.getText().trim().isEmpty() || Integer.parseInt(tPrice.getText()) < 0)
            Error(bundle.getString("priceincorrect"));
        else
        {
            service.setName(tName.getText().trim());
            service.setDuration(Integer.parseInt(tDuration.getText().trim()));
            service.setPrice(Integer.parseInt(tPrice.getText().trim()));
            service.setDescription(tDescription.getText());
            action=true;
            editStage.close();
        }
    }
}