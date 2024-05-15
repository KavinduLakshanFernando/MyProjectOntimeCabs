package lk.ijse.Controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class DashboardController {

    public AnchorPane nodePane;
    public AnchorPane rootNode;


    public void initialize(){

        PauseTransition delay = new PauseTransition(Duration.seconds(0));
        delay.setOnFinished(event -> loadMainDashboard());
        delay.play();

    }

    private void loadMainDashboard() {
        try {
            // Load mainDashboard_form.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainDashboard.fxml"));
            AnchorPane mainDashboard = loader.load();

            // Replace the children of rootNode with the loaded content
            rootNode.getChildren().setAll(mainDashboard);
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setTitle("Dashboard");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/CustomerManage.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Customer Manage");

    }

    @FXML
    private void logoutOnAction(ActionEvent actionEvent) throws IOException {
        // Load the login form
        Parent loginForm = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(loginForm);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
    }

    public void ManageReservationOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageReservation.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Reservation Manage");
    }

    public void ManageDriverOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageDriver.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Driver Manage");
    }

    public void ManageVrhicalrOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageVehicle.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Vehicle Manage");
    }

    public void ManageMaintenanceOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/ManageMaintenance.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Maintenance Manage");
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {

        URL resource = getClass().getResource("/view/MainDashboard.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Dashboard");
    }

    public void gotoProfile(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/Profile.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Profile Manage");
    }


}
