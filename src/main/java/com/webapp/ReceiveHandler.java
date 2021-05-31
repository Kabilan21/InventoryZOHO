package com.webapp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

class ReceiveHandler extends DBHandler {
    static final String tableName = "Receive";
    Scanner scanner = new Scanner(System.in);
    final StaffHandler staffHandler;
    final ProductHandler productHandler;

    ReceiveHandler(StaffHandler staffHandler, ProductHandler productHandler) throws Exception {
        createTableIfNotExists();
        this.staffHandler = staffHandler;
        this.productHandler = productHandler;
    }

    public void createTableIfNotExists() throws Exception {
        String sql = "create table if not exists Receive(id int(11) not null AUTO_INCREMENT,staffID int not null,productID int not null,quantity int not null,timestamp datetime not null default CURRENT_TIMESTAMP,primary key (id),foreign key (staffID) references Staff(id),foreign key (productID) references Product(id) ) ";
        statement.execute(sql);

    }

    public  void removeReceiveByProduct(Product product) throws  Exception{
        String sql = String.format("delete from %s where productID = %d ; ",tableName,product.id);
        statement.executeUpdate(sql);
    }


    Receive deserialize(ResultSet resultSet) throws Exception {
        Staff staff = staffHandler.getStaffByID(resultSet.getInt("staffID"));
        Product product = productHandler.getProductByID(resultSet.getInt("productID"));
        Receive receive = new Receive(staff, product, resultSet.getInt("quantity"));
        receive.id = resultSet.getInt("id");
        receive.timestamp = resultSet.getString("timestamp");
        return receive;
    }

    public Boolean insert(Receive receive) {
        try {
            statement.executeUpdate(receive.save(tableName));
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            return false;
        }
        return true;
    }

    public ArrayList<Receive> getAllData() throws Exception {
        ArrayList<Receive> receives = new ArrayList<Receive>();
        String sql = String.format("select * from %s ;", tableName);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            receives.add(deserialize(resultSet));
        }
        return  receives;
    }
}
