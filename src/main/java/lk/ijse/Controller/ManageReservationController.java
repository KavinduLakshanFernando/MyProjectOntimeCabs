package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Payment;
import lk.ijse.Model.Reservation;
import lk.ijse.Model.Reservation_Details;
import lk.ijse.Model.Service;
import lk.ijse.Repository.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public void initialize(){
        getVehicleCmbValues();
        setPaymentType();
        setServiceCmb();
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
                "Cash", "credit", "debit", "check"
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
    }
}
