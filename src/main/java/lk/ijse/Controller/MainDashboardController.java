package lk.ijse.Controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Model.Dashboard;
import lk.ijse.Repository.DashboardARepo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainDashboardController {

    @FXML
    private Label customercount;

    @FXML
    private Label driverscount;

    @FXML
    private Label lblDate;

    @FXML
    private Label reservationcount;

    @FXML
    private Label vehiclecount;

        public AnchorPane dashID;



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


        public void setDate(){
            LocalDate now = LocalDate.now();
            lblDate.setText(String.valueOf(now));
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
                int cusCount = DashboardARepo.getCustomerCount();
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

