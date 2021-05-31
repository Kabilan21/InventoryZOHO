package com.webapp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

class DispatchHandler extends DBHandler {
    static final String tableName = "Dispatch";
    Scanner scanner = new Scanner(System.in);
    final StaffHandler staffHandler;
    final ProductHandler productHandler;

    DispatchHandler(StaffHandler staffHandler, ProductHandler productHandler) throws Exception {
        createTableIfNotExists();
        this.staffHandler = staffHandler;
        this.productHandler = productHandler;
    }

    public void createTableIfNotExists() throws Exception {
        String sql = "create table if not exists Dispatch(id int(11) not null AUTO_INCREMENT,staffID int not null,productID int not null,quantity int not null,timestamp datetime not null default CURRENT_TIMESTAMP,primary key (id),foreign key (staffID) references Staff(id),foreign key (productID) references Product(id) ) ";
        statement.execute(sql);

    }

    public  void removeDispatchByProduct(Product product) throws  Exception{
        String sql = String.format("delete from %s where productID = %d ; ",tableName,product.id);
        statement.executeUpdate(sql);
    }

    Dispatch deserialize(ResultSet resultSet) throws Exception {
        Staff staff = staffHandler.getStaffByID(resultSet.getInt("staffID"));
        Product product = productHandler.getProductByID(resultSet.getInt("productID"));
        Dispatch dispatch = new Dispatch(staff, product, resultSet.getInt("quantity"));
        dispatch.id = resultSet.getInt("id");
        dispatch.timestamp = resultSet.getString("timestamp");
        return dispatch;
    }

    public Boolean insert(Dispatch dispatch) {
        try {
            statement.executeUpdate(dispatch.save(tableName));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ArrayList<Dispatch> getAllData() throws Exception {
        ArrayList<Dispatch> dispatches = new ArrayList<Dispatch>();
        String sql = String.format("select * from %s ;", tableName);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            dispatches.add(deserialize(resultSet));
        }
        return dispatches;
    }

}
