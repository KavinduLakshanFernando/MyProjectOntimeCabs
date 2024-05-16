package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Insurence;
import lk.ijse.Model.TM.VehicleTMDetails;
import lk.ijse.Model.Vehicle;
import lk.ijse.Repository.InsurenceRepo;
import lk.ijse.Repository.VehicleRegister;
import lk.ijse.Repository.VehicleRepo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageVehicleController {
    public DatePicker txtinsuranceendDate;
    public ComboBox<String> cmbmodel;
    public ComboBox <String> cmbcolor;
    public TextField txtInshurance;
    public TextField txtserchVehicle;
    public DatePicker txtinsuranceStartDate;
    public TextField txtVehicleNumber;

    @FXML
    private TableColumn<?, ?> EndDateCol;

    @FXML
    private TableColumn<?, ?> InshuranceNumberCol;

    @FXML
    private TableColumn<?, ?> StartDateCol;

    @FXML
    private TableColumn<?, ?> VehicleColorCol;

    @FXML
    private TableColumn<?, ?> VehicleModelCol;

    @FXML
    private TableColumn<?, ?> VehicleNumberCol;

    @FXML
    private TableView<VehicleTMDetails> VehicleTable;


    public void initialize() throws SQLException {
        setCellValues();
        loadAllVehicles();
        setCmbmodel();
        setCmbcolur();
        showSelectedUserDetails();
    }

    private void showSelectedUserDetails() {
        VehicleTMDetails selectedUser = VehicleTable.getSelectionModel().getSelectedItem();
        VehicleTable.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            txtVehicleNumber.setText(selectedUser.getVNumber());
            txtInshurance.setText(selectedUser.getINumber());
            cmbcolor.setValue(selectedUser.getColor());
            cmbmodel.setValue(selectedUser.getModel());
            txtinsuranceStartDate.setValue(selectedUser.getStartDate().toLocalDate());
            txtinsuranceendDate.setValue(selectedUser.getEndDate().toLocalDate());
        }
    }   // සිලෙක්ට් කරාම ෆිල් වෙන එක

    public void loadAllVehicles() throws SQLException {
        ObservableList<VehicleTMDetails> obList = FXCollections.observableArrayList();

        try {
            List<VehicleTMDetails> vehicleList = VehicleRepo.getAll();

            for (VehicleTMDetails details : vehicleList){
                obList.add(details);
            }
            VehicleTable.setItems(obList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void setCellValues(){
        VehicleNumberCol.setCellValueFactory(new PropertyValueFactory<>("VNumber"));
        InshuranceNumberCol.setCellValueFactory(new PropertyValueFactory<>("INumber"));
        VehicleColorCol.setCellValueFactory(new PropertyValueFactory<>("Color"));
        VehicleModelCol.setCellValueFactory(new PropertyValueFactory<>("Model"));
        StartDateCol.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        EndDateCol.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
    }

    public void AddVehicleOnAction(ActionEvent actionEvent) {
        String vid = txtVehicleNumber.getText();
        String iId = txtInshurance.getText();
        String model = cmbmodel.getValue();
        String colur = cmbcolor.getValue();
        Date stsate = Date.valueOf(txtinsuranceStartDate.getValue());
        Date enddate = Date.valueOf(txtinsuranceendDate.getValue());
        String status = "active";

        Insurence insurence = new Insurence(iId, stsate, enddate);
        Vehicle vehicle = new Vehicle(vid, model, colur, iId,status);

       try {
           boolean issaved = VehicleRegister.regiVehicle(insurence,vehicle);
          if(issaved){
             new Alert(Alert.AlertType.CONFIRMATION,"data saved").show();
             loadAllVehicles();
             clear();
          }else{
              new Alert(Alert.AlertType.ERROR,"data not saved").show();
          }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }
   }

    private void setCmbmodel() {
        ObservableList<String> modellist = FXCollections.observableArrayList(
                "suzuki Alto", "Wagon R", "Suzuki Spacia", "Toyota Vitz","Toyota Aqua","Toyota Priys"
                ,"Toyota Axio","Toyot Premio","Honda Vezel","Toyota KDH"
        );

        cmbmodel.setItems(modellist);
    }

    private void setCmbcolur() {
        ObservableList<String> colurlist = FXCollections.observableArrayList(
                "white", "red", "black", "blue"
        );

        cmbcolor.setItems(colurlist);
    }

    public void DeleteVehicleOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtVehicleNumber.getText();
        String iid = txtInshurance.getText();
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
           boolean isdelete = VehicleRepo.delete(id);
           if (isdelete){
               boolean isdelete2 = InsurenceRepo.delete(iid);
               if (isdelete2){
                   connection.commit();
                   new Alert(Alert.AlertType.CONFIRMATION,"vehicle deleted").show();
                   loadAllVehicles();
               }
           }else {
               connection.rollback();
           }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"vehicle not delete").show();
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public void UpdateVehicleOnAction(ActionEvent actionEvent) {
        String vid = txtVehicleNumber.getText();
        String iId = txtInshurance.getText();
        String model = cmbmodel.getValue();
        String colur = cmbcolor.getValue();
        Date stsate = Date.valueOf(txtinsuranceStartDate.getValue());
        Date enddate = Date.valueOf(txtinsuranceendDate.getValue());

    }

    public void SearchvehicleOnAction(ActionEvent actionEvent) throws SQLException {
        String searchid = txtserchVehicle.getText();

        Vehicle vehicle = VehicleRepo.searchData(searchid);
        if(vehicle!=null) {

            try {
                assert vehicle != null;
                Insurence insurence = InsurenceRepo.seachdata(vehicle.getIId());
                txtVehicleNumber.setText(vehicle.getId());
                assert insurence != null;
                txtInshurance.setText(insurence.getI_id());
                cmbmodel.setValue(vehicle.getModel());
                cmbcolor.setValue(vehicle.getColur());
                txtinsuranceStartDate.setValue(insurence.getStdate().toLocalDate());
                txtinsuranceendDate.setValue(insurence.getEnddate().toLocalDate());

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"cant fimd this vehicle").show();
        }
    }

    public void clear(){
        txtVehicleNumber.clear();
        txtInshurance.clear();
        txtinsuranceStartDate.setValue(null);
        txtinsuranceendDate.setValue(null);
        cmbcolor.setValue(null);
        cmbmodel.setValue(null);
    }

    public void CelarVehicleDetails(ActionEvent actionEvent) {
        clear();
    }
}
