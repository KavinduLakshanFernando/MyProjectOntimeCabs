package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Driver;
import lk.ijse.Model.Maintenance;
import lk.ijse.Model.Maintenance_Details;
import lk.ijse.Model.TM.DriverTM;
import lk.ijse.Model.TM.MaintenanceTM;
import lk.ijse.Model.TM.ReservationTM;
import lk.ijse.Repository.*;

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


    @FXML
    private TableView<MaintenanceTM> MaintenanceTable;

    @FXML
    private TableColumn<?, ?> vnumbercol;

    @FXML
    private TableColumn<?, ?> desccol;

    @FXML
    private TableColumn<?, ?> maintenancenumbercol;

    @FXML
    private TableColumn<?, ?> mcostcol;

    @FXML
    private TableColumn<?, ?> mdatecol;


    public void initialize(){
        getVehicleIds();
        setCellValueFactory();
        loadAllMaintenance();
        getCurrentMaintenanceId();

    }
    private void loadAllMaintenance() {
        ObservableList<MaintenanceTM> obList = FXCollections.observableArrayList();

        try {
            List<MaintenanceTM> menList = MaintenanceRepo.getAll();
            for (MaintenanceTM men : menList) {
                MaintenanceTM tm = new MaintenanceTM(
                        men.getV_id(),
                        men.getM_id(),
                        men.getDesc(),
                        men.getDate(),
                        men.getCost()
                );
                obList.add(tm);
            }

            MaintenanceTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentMaintenanceId() {
        try {
            String currentId = MaintenanceRepo.getCurrentId();

            String nextMaintenanceId = generateNextMaintenanceid(currentId);
            txtMaintenanceId.setText(nextMaintenanceId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextMaintenanceid(String currentId) {
        if(currentId != null && !currentId.isEmpty()) {
            String[] split = currentId.split("M");
            if (split.length > 1) {
                int idNum = Integer.parseInt(split[1]);
                return "M0" + String.format("%02d", ++idNum);
            }
        }
        return "M001"; // Default starting ID
    }

    private void setCellValueFactory() {
        vnumbercol.setCellValueFactory(new PropertyValueFactory<>("V_id"));
        maintenancenumbercol .setCellValueFactory(new PropertyValueFactory<>("M_id"));
        desccol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        mdatecol.setCellValueFactory(new PropertyValueFactory<>("date"));
        mcostcol.setCellValueFactory(new PropertyValueFactory<>("cost"));
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

        Maintenance maintenance = new Maintenance(mid,vid, desc, cost, mdate);
        Maintenance_Details maintenanceDetails = new Maintenance_Details(vid, mid);

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean issaved = MaintenanceRepo.save(maintenance);
            if (issaved){
                boolean issaved2 = Maintenance_DetailsRepo.save(maintenanceDetails);
                if (issaved2){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Maintenance Data Saved").show();
                    loadAllMaintenance();
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
               new Alert(Alert.AlertType.CONFIRMATION,"Delete Maintenance Data").show();
               loadAllMaintenance();
           }else {
               new Alert(Alert.AlertType.ERROR,"Not Delete Maintenance Data").show();

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
            new Alert(Alert.AlertType.ERROR,"Cant Find This Maintenence").show();
        }


    }
}
