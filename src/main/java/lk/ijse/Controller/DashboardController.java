package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Model.Dashboard;
import lk.ijse.Repository.DashboardARepo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;

public class DashboardController {
    public AnchorPane dashID;
    public AnchorPane acCustomer;
    public AnchorPane acRv;
    public AnchorPane acVehicle;
    public Label lblDate;
    public AnchorPane acDriver1;
    public Label reservationcount;
    public Label vehiclecount;
    public Label customercount;
    public Label driverscount;


    public void initialize(){
        setDate();
        setVehicleCount();
        setCustomerCount();
        setDriverscount();
        setReservationCount();
    }

    private void setReservationCount() {

        try {
            DashboardARepo dashboardARepo = new DashboardARepo();
            Dashboard dashboard = new Dashboard();

            int resCount = DashboardARepo.getResCount(dashboard);

            reservationcount.setText(String.valueOf(resCount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void ManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/CustomerManage.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        dashID.getChildren().clear();
        dashID.getChildren().add(load);


    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("LoginForm");

        stage.show();
    }

    public void ManageReservationOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageReservation.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        dashID.getChildren().clear();
        dashID.getChildren().add(load);
    }


    public void ManageDriverOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageDriver.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        dashID.getChildren().clear();
        dashID.getChildren().add(load);
    }

    public void ManageVrhicalrOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageVehicle.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        dashID.getChildren().clear();
        dashID.getChildren().add(load);
    }

    public void ManageMaintenanceOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageMaintenance.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        dashID.getChildren().clear();
        dashID.getChildren().add(load);
    }

    public void setDate(){
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    public void gotoProfile(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/Profile.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        dashID.getChildren().clear();
        dashID.getChildren().add(load);
    }

    public void setVehicleCount(){
        try {
            DashboardARepo dashboardARepo = new DashboardARepo();
            Dashboard dashboard = new Dashboard();

            int VehicleCount = DashboardARepo.getVehicleCount(dashboard);

            vehiclecount.setText(String.valueOf(VehicleCount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   public void setCustomerCount(){
        try{
            DashboardARepo dashboardARepo = new DashboardARepo();
            Dashboard dashboard = new Dashboard();

            int cusCount = DashboardARepo.getCustomerCount(dashboard);
            customercount.setText(String.valueOf(cusCount));

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    public void setDriverscount(){
        try{
            DashboardARepo dashboardARepo = new DashboardARepo();
            Dashboard dashboard = new Dashboard();

            int cusCount = DashboardARepo.getDriversCount(dashboard);
            driverscount.setText(String.valueOf(cusCount));

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
