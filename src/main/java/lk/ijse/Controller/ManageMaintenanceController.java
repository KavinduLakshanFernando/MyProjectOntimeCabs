package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Maintenance;
import lk.ijse.Model.Maintenance_Details;
import lk.ijse.Repository.MaintenanceRepo;
import lk.ijse.Repository.Maintenance_DetailsRepo;
import lk.ijse.Repository.VehicleRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageMaintenanceController {

    public TextField MaintenanceCost;
    public TextField txtMaintenanceId;
    public TextField txtDesc;
    public TextField txtserchMaintenance;
    public DatePicker MaintenanceDate;
    public ComboBox Vehiclenumcmb;


    public void initialize(){
        getVehicleIds();
    }
    private void getVehicleIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = VehicleRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            Vehiclenumcmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void SaveMaintenance(ActionEvent actionEvent) throws SQLException {
        String vid = (String) Vehiclenumcmb.getValue();
        String cost = MaintenanceCost.getText();
        String mid = txtMaintenanceId.getText();
        String desc = txtDesc.getText();
        String mdate = String.valueOf(MaintenanceDate.getValue());

        Maintenance maintenance = new Maintenance(mid, desc, cost, mdate);
        Maintenance_Details maintenanceDetails = new Maintenance_Details(vid, mid);

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
try {
    boolean issaved = MaintenanceRepo.save(maintenance);
    if (issaved){
        boolean issaved2 = Maintenance_DetailsRepo.save(maintenanceDetails);
        if (issaved2){
            connection.commit();
            new Alert(Alert.AlertType.CONFIRMATION,"all data saved").show();
        }else {
            connection.rollback();
        }
    }else {
        connection.rollback();
    }
}catch (Exception e){
    connection.rollback();
    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
}finally{
    connection.setAutoCommit(true);
}
    }

    public void DeletMaintenance(ActionEvent actionEvent) {
        String mid = txtMaintenanceId.getText();
        try {
            boolean isdelete = MaintenanceRepo.delete(mid);
           if (isdelete){
               new Alert(Alert.AlertType.CONFIRMATION,"mantanence deleted").show();
           }else {
               new Alert(Alert.AlertType.ERROR,"mantanence not deleted").show();

           }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }


    public void ClearMaintenance(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        Vehiclenumcmb.setValue(null);
        MaintenanceCost.clear();
        txtMaintenanceId.clear();
        txtDesc.clear();
        txtserchMaintenance.clear();
        MaintenanceDate.setValue(null);
    }

    public void SearchmaintenanceOnAction(ActionEvent actionEvent) throws SQLException {
        String mid = txtserchMaintenance.getText();
        String vid = Maintenance_DetailsRepo.getVid(mid);
        if (vid != null){
            Maintenance maintenance = MaintenanceRepo.searchData(mid);
            if (maintenance != null){
                txtMaintenanceId.setText(maintenance.getM_id());
                Vehiclenumcmb.setValue(vid);
                MaintenanceCost.setText(maintenance.getCost());
                txtDesc.setText(maintenance.getDescription());
                MaintenanceDate.setValue(LocalDate.parse(maintenance.getDate()));
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"vant find this maintenence").show();
        }


    }
}
