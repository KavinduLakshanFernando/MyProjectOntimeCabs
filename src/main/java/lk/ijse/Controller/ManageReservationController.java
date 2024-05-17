package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Payment;
import lk.ijse.Model.Reservation;
import lk.ijse.Model.Reservation_Details;
import lk.ijse.Model.Service;
import lk.ijse.Model.TM.ReservationTM;
import lk.ijse.Repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class ManageReservationController {

    public TextField reservationid;
    public TextField txtpaymentid;
    public TextField customerNic;
    public ComboBox ServiceCmb;
    public TextField txtSearchReservation;
    public ComboBox vehicleNumbercmb;
    public ComboBox paymentMethodcmb;
    public TextField PaymentAmount;
    public DatePicker PaymentDate;
    public DatePicker ReservationDate;
    public Rectangle ReservationType;
    public Text lblCustomerName;

    @FXML
    private TableView<ReservationTM> tblReservation;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colCustomerNic;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colServiceType;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    public void initialize(){
        getVehicleCmbValues();
        setPaymentType();
        setServiceCmb();
        getCurrentReservationId();
        setCellValueFactory();
        loadAllReservation();
    }

    private void loadAllReservation() {
        ObservableList<ReservationTM> obList = FXCollections.observableArrayList();

        try {
            List<ReservationTM> resList = ReservasionRepo.getAll();
            for (ReservationTM res : resList) {
                ReservationTM tm = new ReservationTM(
                        res.getRe_id(),
                        res.getP_id(),
                        res.getVehicleId(),
                        res.getServiceType(),
                        res.getCusNic(),
                        res.getName(),
                        res.getAmount(),
                        res.getPaymentType(),
                        res.getDate()
                );
                obList.add(tm);
            }

            tblReservation.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory(){
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("P_id"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("Re_id"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colCustomerNic.setCellValueFactory(new PropertyValueFactory<>("cusNic"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
    }


    private void getCurrentReservationId() {
        try {
            String currentId = ReservasionRepo.getCurrentId();

            String nextReservationId = generateNextReservationId(currentId);
            reservationid.setText(nextReservationId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextReservationId(String currentId) {
        if(currentId != null && !currentId.isEmpty()) {
            String[] split = currentId.split("R");
            if (split.length > 1) {
                int idNum = Integer.parseInt(split[1]);
                return "R0" + String.format("%02d", ++idNum);
            }
        }
        return "R001"; // Default starting ID
    }

    public void btnCusNicSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String nic = customerNic.getText();

            String  name = CustomerRepo.nicSearch(nic);
            if (name != null) {
                lblCustomerName.setText(name);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
    }
    private void setServiceCmb() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = ServiceRepo.getNamelist();
            for(String id : idList) {
                obList.add(id);
            }
            ServiceCmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getVehicleCmbValues() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = VehicleRepo.getIds();
            for(String id : idList) {
                obList.add(id);
            }
            vehicleNumbercmb.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPaymentType() {
        ObservableList<String> colurlist = FXCollections.observableArrayList(
                "Cash", "Credit", "Debit", "Check"
        );
        paymentMethodcmb.setItems(colurlist);
    }

    public void AddreservatuinOnAction(ActionEvent actionEvent) throws SQLException {
        String rid = reservationid.getText();
        String pid = txtpaymentid.getText();
        String cid = customerNic.getText();
        String servicename = (String) ServiceCmb.getValue();
        String vid = (String) vehicleNumbercmb.getValue();
        String ptype = (String) paymentMethodcmb.getValue();
        String amount= PaymentAmount.getText();
        String pdate = String.valueOf(PaymentDate.getValue());
        String rdate = String.valueOf(ReservationDate.getValue());

        Payment payment = new Payment(pid, amount,pdate, ptype);
        String sid = ServiceRepo.getId(servicename);
        Reservation reservation = new Reservation(rid, pid, sid, cid);
        Reservation_Details reservationDetails = new Reservation_Details(rid, vid, rdate);

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{
            boolean issaved = PaymentRepo.savedata(payment);
            if (issaved){
                boolean issaved2 = Reservation.savedata(reservation);
                if (issaved2){
                    boolean issaved3 = Reservation_DetailsRepo.savedata(reservationDetails);
                    if (issaved3){
                        clear();
                        initialize();
                        new Alert(Alert.AlertType.CONFIRMATION,"Data Saved").show();
                        connection.commit();
                    }else {
                        connection.rollback();
                        new Alert(Alert.AlertType.ERROR,"data save failed 3").show();
                    }
                }else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"data save failed 2").show();
                }
            }else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"data saved failed").show();
            }
        }catch (Exception e ){
            connection.rollback();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }finally{
            connection.setAutoCommit(true);
        }
    }

    public void DeletereservatuinOnAction(ActionEvent actionEvent) throws SQLException {
        String rid = reservationid.getText();
        String pid = txtpaymentid.getText();

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isdelete = ReservasionRepo.delecte(rid);
            if(isdelete){
                boolean isdelete2 = PaymentRepo.delete(pid);
                if(isdelete2){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Reservation deleted").show();
                    loadAllReservation();
                }
            }else {
                connection.rollback();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Reservation Not deleted").show();
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public void searchOnActionReservation(ActionEvent actionEvent) throws SQLException {
        String Rid = reservationid.getText();
        Reservation reservation = ReservasionRepo.search(Rid);

        reservationid.setText(reservation.getRe_id());
        customerNic.setText(reservation.getCu_id());
        txtpaymentid.setText(reservation.getP_id());

        Payment payment = PaymentRepo.search(reservation.getP_id());
        PaymentAmount.setText(payment.getAmount());
        PaymentDate.setValue(LocalDate.parse(payment.getDate()));
        paymentMethodcmb.setValue(payment.getPayment_method());

    }

    public void clearReservationOnAction(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        reservationid.clear();
        txtpaymentid.clear();
        customerNic.clear();
        txtSearchReservation.clear();
        PaymentAmount.clear();

        ServiceCmb.setValue(null);
        vehicleNumbercmb.setValue(null);
        ReservationDate.setValue(null);
        paymentMethodcmb.setValue(null);
        PaymentDate.setValue(null);
        lblCustomerName.setText(null);

    }

    public void btnBillOnAction(ActionEvent actionEvent) throws SQLException, JRException {
        printBill();
    }

    private void printBill() throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/reservationKavindu1.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        data.put("Re_id", reservationid.getText());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
