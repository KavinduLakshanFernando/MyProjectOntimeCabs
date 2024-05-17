package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {













    public static String nicSearch(String nic) throws SQLException {
        String sql = "SELECT Name FROM customer WHERE nic = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, nic);
        ResultSet resultSet = pstm.executeQuery();



        if(resultSet.next()) {
            String cusName = resultSet.getString(1);

            return cusName;
        }
        return null;
    }











    public static  List<Customer> getAll() throws SQLException {
        String sql  ="select * from customer";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> CUsList = new ArrayList<>();

        while (resultSet.next()){
            String CustomerID  = resultSet.getString(1);
            String Name  = resultSet.getString(2);
            String Adress  = resultSet.getString(3);
            String Contact  = resultSet.getString(4);

            Customer customer = new Customer(CustomerID,Name,Adress,Contact);
            CUsList.add(customer);
        }
        return CUsList;
    }
    public static boolean save(Customer customer) throws SQLException {

        String sql ="insert into customer values(?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getNic());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getTel());

        return pstm.executeUpdate() > 0;
    }



    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE nic = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET Name = ?, Address = ?, Tel = ? WHERE nic = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getTel());
        pstm.setObject(4, customer.getNic());

        return pstm.executeUpdate() > 0;
    }

    public static Customer search(String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE nic = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);

            Customer customer = new Customer(cus_id, name, address, tel);

            return customer;
        }

        return null;
    }


















}

