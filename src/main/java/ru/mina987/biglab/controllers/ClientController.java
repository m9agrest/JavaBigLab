package ru.mina987.biglab.controllers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.mina987.biglab.dao.ServiceDao;
import ru.mina987.biglab.domains.Service;
public class ClientController {
    private Stage clientStage;
    public void setEditStage(Stage clientStage) {
        this.clientStage = clientStage;
    }
    @FXML
    private TableColumn<Service, String> tcDescription;
    @FXML
    private TableColumn<Service, Integer> tcDuration;
    @FXML
    private TableColumn<Service, String> tcName;
    @FXML
    private TableColumn<Service, Integer> tcPrice;
    @FXML
    private TableView<Service> tvServices;
    private ServiceDao dao;
    public ClientController(){
        try {
            dao = new ServiceDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected ObservableList<Service> services = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        services.addAll(dao.findALl());
        tcName.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getName()));
        tcDuration.setCellValueFactory(s -> new SimpleObjectProperty<Integer>(s.getValue().getDuration()));
        tcPrice.setCellValueFactory(s -> new SimpleObjectProperty<Integer>(s.getValue().getPrice()));
        tcDescription.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getDescription()));
        tvServices.setItems(services);
        tvServices.getSortOrder().add(tcName);
        tvServices.getSortOrder().add(tcDuration);
        tvServices.getSortOrder().add(tcPrice);
    }
}