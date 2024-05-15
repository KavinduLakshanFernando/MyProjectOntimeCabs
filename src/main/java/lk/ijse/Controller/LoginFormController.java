package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUserName;
    public TextField txtPassword;

    public int k = 0;

    public void LoginFormOnAction(ActionEvent actionEvent) throws IOException {
        final String uname = "k";
        final String pass = "123";

        String username = txtUserName.getText();
        String password = txtPassword.getText();

        if(username.equals(uname)){
            if (password.equals(pass)){
                navigateToDashboard();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"password Is wromng !!!").show();
            }
            }else{
            new Alert(Alert.AlertType.ERROR,"user name wrong !!!").show();
        }
    }

    private void navigateToDashboard() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.show();
    }
}
