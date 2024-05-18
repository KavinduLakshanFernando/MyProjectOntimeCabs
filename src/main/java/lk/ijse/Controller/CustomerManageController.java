package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Model.Customer;
import lk.ijse.Model.TM.CustomerTM;
import lk.ijse.Repository.CustomerRepo;
import lk.ijse.Repository.ReservasionRepo;
import lk.ijse.Util.Regex;

import java.sql.SQLException;
import java.util.List;

import static com.jfoenix.svg.SVGGlyphLoader.clear;

public class CustomerManageController {
    public TextField txtName;
    public TextField txtPhone;
    public TextField txtAddress;
    public TextField txtId;
    public TextField txtserchCustomer;
    @FXML
    private TableColumn<?, ?> addresscol;

    @FXML
    private TableColumn<?, ?> contactcol;

    @FXML
    private TableColumn<?, ?> namecol;

    @FXML
    private TableColumn<?, ?> niccol;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    public void initialize(){
        setCellValues();
        loadCustomer();

    }

    public void nameKeyRelaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME, txtName);
    }

    public void nicKeyRelaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NIC, txtId);
    }

    public void addressKeyRelaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.ADDRESS, txtAddress);
    }

    public void telKeyRelaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.TEL, txtPhone);
    }

    public boolean isValied(){
        boolean nameValid = Regex.setTextColor(lk.ijse.Util.TextField.NAME, txtName);
        boolean nicValid = Regex.setTextColor(lk.ijse.Util.TextField.NIC, txtId);
        boolean addressValid = Regex.setTextColor(lk.ijse.Util.TextField.ADDRESS, txtAddress);
        boolean telValid = Regex.setTextColor(lk.ijse.Util.TextField.TEL, txtPhone);

        return nameValid && nicValid && addressValid && telValid;
    }

    private void loadCustomer(){
        ObservableList<CustomerTM> cusList = FXCollections.observableArrayList();
        try {
            List<Customer> all = CustomerRepo.getAll();
            for (Customer customer : all){

                CustomerTM cusTM = new CustomerTM(customer.getNic(), customer.getName(), customer.getAddress(), customer.getTel());
                cusList.add(cusTM);
                tblCustomer.setItems(cusList);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues(){
        niccol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        contactcol.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }

    public void SavebtnOnAction(ActionEvent actionEvent) throws SQLException {

        if(isValied()) {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();

            if (id.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please Fill In All Fields!").show();
                return;
            }
            Customer customer = new Customer(id, name, address, phone);
            try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!").show();
                    loadCustomer();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Saved").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            // Show error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }

    }

    public void DeletebtnOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if (isDeleted) {
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
//                loadCustomer();

                clear();



//                tblCustomer.refresh();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void UpdatebtnOnAction(ActionEvent actionEvent) throws SQLException {
        if(isValied()) {

            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String tel = txtPhone.getText();

            boolean isUpdated = CustomerRepo.update(new Customer(id, name, address,tel));

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
                loadCustomer();
                clear();
            }
        } else {
            // Show error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }

    }

    public void ClearbtnOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtserchCustomer.getText();

        Customer customer = CustomerRepo.search(id);
        if (customer != null) {
            txtId.setText(customer.getNic());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtPhone.setText(customer.getTel());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Found!").show();
        }
    }

    public void clear() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtPhone.clear();

        txtserchCustomer.clear();

    }
}
