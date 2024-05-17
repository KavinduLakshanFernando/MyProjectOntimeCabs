package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Model.Driver;
import lk.ijse.Model.TM.DriverTM;
import lk.ijse.Repository.CustomerRepo;
import lk.ijse.Repository.DriverRepo;
import lk.ijse.Repository.VehicleRepo;

import java.sql.SQLException;
import java.util.List;

public class ManageDriverController {
    public TextField Driverid;
    public TextField DriverName;
    public TextField DriverPhone;
    public TextField DriverAddress;
    public ComboBox VehicleNumCmb;
    public TextField txtserchDriver;

    @FXML
    private TableView<DriverTM> DriverTable;
    @FXML
    private TableColumn<?, ?> DriverNicCol;
    @FXML
    private TableColumn<?, ?> DriverNameCol;
    @FXML
    private TableColumn<?, ?> DriverAddressCol;
    @FXML
    private TableColumn<?, ?> DriverContactCol;
    @FXML
    private TableColumn<?, ?> VehicleNumberCol;

    private void loadAllDrivers() {
        ObservableList<DriverTM> obList = FXCollections.observableArrayList();

        try {
            List<Driver> driverList = DriverRepo.getAll();
            for (Driver driver : driverList) {
                DriverTM tm = new DriverTM(
                        driver.getId(),
                        driver.getName(),
                        driver.getAddress(),
                        driver.getContact(),
                        driver.getVnumber()
                );
                obList.add(tm);
            }
            DriverTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        setCellValueFactory();
        loadAllDrivers();
        getVehicleIds();
    }

    private void setCellValueFactory() {
        DriverNicCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        DriverNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        DriverAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        DriverContactCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
        VehicleNumberCol.setCellValueFactory(new PropertyValueFactory<>("Vnumber"));
    }


    private void getVehicleIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = VehicleRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            VehicleNumCmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void SaveDriver(ActionEvent actionEvent) {
        String id = Driverid.getText();
        String name = DriverName.getText();
        String address = DriverAddress.getText();
        String contact = DriverPhone.getText();
        String vnumber = (String) VehicleNumCmb.getValue();
        String status = "active";

        Driver driver = new Driver(id, name, address, contact, vnumber,status);
        try{
            boolean issaved = DriverRepo.save(driver);
            if (issaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Driver Is Saved").show();
                loadAllDrivers();
            }else {
                new Alert(Alert.AlertType.ERROR,"Driver Not Saved").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void DeleteDriver(ActionEvent actionEvent) {
        String id = txtserchDriver.getText();
        try {
            boolean isdeleted = DriverRepo.delete(id);
            if (isdeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Driver Is Deleted").show();
                loadAllDrivers();
            }else{
                new Alert(Alert.AlertType.ERROR,"Driver Not Delete").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Cant Delete This Driver").show();
        }
    }

    public void UpdateDriver(ActionEvent actionEvent) {
        String id = Driverid.getText();
        String name = DriverName.getText();
        String address = DriverAddress.getText();
        String contact = DriverPhone.getText();
        String vnumber = (String) VehicleNumCmb.getValue();
        String status = "active";

        Driver driver = new Driver(id, name, address, contact, vnumber,status);
        try{
            boolean issaved = DriverRepo.update(driver);
            if (issaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Driver Is Update").show();
                loadAllDrivers();
            }else {
                new Alert(Alert.AlertType.ERROR,"Driver Not Update").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void ClearDriver(ActionEvent actionEvent) {
        Driverid.setText(null);
        DriverName.setText(null);
        DriverPhone.setText(null);
        DriverAddress.setText(null);
        VehicleNumCmb.setValue(null);
        txtserchDriver.setText(null);

    }

    public void SearchDriverOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtserchDriver.getText();

        Driver driver = DriverRepo.searchData(id);

        if (driver != null){
            Driverid.setText(driver.getId());
            DriverName.setText(driver.getName());
            DriverPhone.setText(driver.getContact());
            DriverAddress.setText(driver.getAddress());
            VehicleNumCmb.setValue(driver.getVnumber());

        }
    }
}
