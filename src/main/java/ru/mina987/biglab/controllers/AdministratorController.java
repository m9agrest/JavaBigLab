package ru.mina987.biglab.controllers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mina987.biglab.MainApplication;
import ru.mina987.biglab.dao.EmployerDao;
import ru.mina987.biglab.dao.ServiceDao;
import ru.mina987.biglab.domains.Employer;
import ru.mina987.biglab.domains.Service;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class AdministratorController {
    private Stage administratorStage;
    public void setEditStage(Stage administratorStage) {
        this.administratorStage = administratorStage;
    }
    private ServiceDao serviceDao;
    private EmployerDao employerDao;
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
    @FXML
    private TableColumn<Employer, String> tcAddress;
    @FXML
    private TableColumn<Employer, String> tcFIO;
    @FXML
    private TableColumn<Employer, String> tcPhone;
    @FXML
    private TableView<Employer> tvEmployers;
    private static LoggerFactory LoggerFactory;
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);
    public AdministratorController(){
        try {
            serviceDao = new ServiceDao();
            employerDao = new EmployerDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected ObservableList<Service> services = FXCollections.observableArrayList();
    protected ObservableList<Employer> employers = FXCollections.observableArrayList();
    private ResourceBundle bundle = ResourceBundle.getBundle("administrator",Locale.getDefault());
    @FXML
    void initialize() {
        services.addAll(serviceDao.findALl());
        tcName.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getName()));
        tcDuration.setCellValueFactory(s -> new SimpleObjectProperty<Integer>(s.getValue().getDuration()));
        tcPrice.setCellValueFactory(s -> new SimpleObjectProperty<Integer>(s.getValue().getPrice()));
        tcDescription.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getDescription()));
        tvServices.setItems(services);
        tvServices.getSortOrder().add(tcName);
        tvServices.getSortOrder().add(tcDuration);
        tvServices.getSortOrder().add(tcPrice);
        employers.addAll(employerDao.findALl());
        tcFIO.setCellValueFactory(s -> new SimpleStringProperty(
            s.getValue().getSurName() + ' ' +
               s.getValue().getName() + ' ' +
               s.getValue().getPatronym())
        );
        tcPhone.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getPhone()));
        tcAddress.setCellValueFactory(s ->{
            if (s.getValue().getAppartment()==null)
                return new SimpleStringProperty(
                    s.getValue().getCity() + ", " +
                            s.getValue().getStreet() + ", " +
                            s.getValue().getHouse());
            else
                return new SimpleStringProperty(
                        s.getValue().getCity() + ", " +
                                s.getValue().getStreet() + ", " +
                                s.getValue().getHouse() + ", " +
                                s.getValue().getAppartment());
        }
        );
        tvEmployers.setItems(employers);
        tvEmployers.getSortOrder().add(tcFIO);
        tvEmployers.getSortOrder().add(tcPhone);
        tvEmployers.getSortOrder().add(tcAddress);
    }
    private boolean showEmployerDialog(Employer employer) {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("employer-addEdit.fxml"),bundle);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage addStage = new Stage();
            addStage.setTitle(bundle.getString("employerinfo"));
            addStage.setScene(scene);
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.initOwner(MainApplication.getMainStage());
            EditEmployerController editEmployerController = loader.getController();
            editEmployerController.setEmployer(employer);
            editEmployerController.setEditStage(addStage);
            editEmployerController.setBundle(bundle);
            addStage.showAndWait();
            return editEmployerController.isAction();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    private boolean showServiceDialog(Service service) {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("service-addEdit.fxml"),bundle);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage addStage = new Stage();
            addStage.setTitle(bundle.getString("serviceinfo"));
            addStage.setScene(scene);
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.initOwner(MainApplication.getMainStage());
            EditServiceController editServiceController = loader.getController();
            editServiceController.setService(service);
            editServiceController.setEditStage(addStage);
            editServiceController.setBundle(bundle);
            addStage.showAndWait();
            return editServiceController.isAction();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    @FXML
    void onAddEmployer(ActionEvent event) {
        Employer employer = new Employer();
        if(showEmployerDialog(employer)){
            employerDao.save(employer);
            if(employer.getAppartment()==null)
                employer.setAppartment(0);
            employers.add(employer);
            tvEmployers.sort();
        }
    }
    @FXML
    void onAddService(ActionEvent event) {
        Service service = new Service();
        if(showServiceDialog(service)){
            serviceDao.save(service);
            services.add(service);
            tvServices.sort();
        }
    }

    @FXML
    void onEditEmployer(ActionEvent event) {
        Employer employer = tvEmployers.getSelectionModel().getSelectedItem();
        if (employer != null){
            if(showEmployerDialog(employer)){
                employerDao.update(employer);
                employers.clear();
                employers.addAll(employerDao.findALl());
                tvEmployers.sort();
            }
        }
        else
            Error(bundle.getString("employernotchoice"));
    }
    @FXML
    void onEditService(ActionEvent event) {
        Service service = tvServices.getSelectionModel().getSelectedItem();
        if (service != null){
            if(showServiceDialog(service)){
                serviceDao.update(service);
                services.clear();
                services.addAll(serviceDao.findALl());
                tvServices.sort();
            }
        }
        else
            Error(bundle.getString("servicenotchoice"));
    }
    @FXML
    void onRemoveEmployer(ActionEvent event) {
        Employer employer = tvEmployers.getSelectionModel().getSelectedItem();
        if(showRemoveDialog("employer")){
            tvEmployers.getItems().remove(tvEmployers.getSelectionModel().getSelectedIndex());
            employerDao.deleteById(employer.getEmployerID());
            tvEmployers.sort();
        }
    }
    @FXML
    void onRemoveService(ActionEvent event) {
        Service service = tvServices.getSelectionModel().getSelectedItem();
        if(showRemoveDialog("service")){
            tvServices.getItems().remove(tvServices.getSelectionModel().getSelectedIndex());
            serviceDao.deleteById(service.getServiceID());
            tvServices.sort();
        }
    }
    private void Error(String text){
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(bundle.getString("error"));
        alert.setContentText(null);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
    private boolean showRemoveDialog(String removeditem) {

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("removeform.fxml"),bundle);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage remove = new Stage();
            remove.setResizable(false);
            remove.setTitle(bundle.getString("remove"+removeditem));
            remove.setScene(scene);
            remove.initModality(Modality.APPLICATION_MODAL);
            remove.initOwner(MainApplication.getMainStage());
            RemoveController removeController = loader.getController();
            removeController.setRemoveStage(remove);
            remove.showAndWait();
            return removeController.isRemove();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}