package com.webapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class ProductHandler extends DBHandler {
    static final String tableName = "Product";
    Scanner scanner = new Scanner(System.in);

    ProductHandler() throws Exception {
        createTableIfNotExists();
    }

    Product deserialize(ResultSet resultSet) throws Exception {
        Product product = new Product(resultSet.getString("name"), resultSet.getInt("price"),
                resultSet.getInt("quantity"), resultSet.getString("description"));
        product.id = resultSet.getInt("id");
        return product;
    }

    public void createTableIfNotExists() throws Exception {
        String sql = "create table if not exists Product(id int(11) not null AUTO_INCREMENT,name varchar(120) not null,price double(11,2) not null,quantity int(11) not null,description varchar(240),primary key (id),unique (name) ) ";
        statement.execute(sql);

    }

    public Product insertProduct(Product product) {
        try {
            statement.executeUpdate(product.save(tableName));
            return getProductByName(product.name);

        } catch (Exception e) {
            System.out.println("Product already exists..");
            return null;
        }
    }

    public ArrayList<Product> getAllProducts() throws Exception {
        ArrayList<Product> products = new ArrayList<Product>();
        ResultSet resultSet = statement.executeQuery("select * from " + tableName + " ;");
        while (resultSet.next()) {
            products.add(deserialize(resultSet));
        }
        return  products;
    }

    public void removeProductByID(int id) throws Exception {
        StaffHandler staffHandler = new StaffHandler();
        DispatchHandler dispatchHandler = new DispatchHandler(staffHandler,this);
        ReceiveHandler receiveHandler = new ReceiveHandler(staffHandler,this);
        dispatchHandler.removeDispatchByProduct(getProductByID(id));
        receiveHandler.removeReceiveByProduct(getProductByID(id));
        String sql = String.format("delete from %s where id = %d;", tableName, id);
        System.out.println(sql);
        statement.executeUpdate(sql);
    }

    public Product getProductObjectFromUser() {
        Product product = new Product();
        System.out.print("enter product name :");
        product.name = scanner.nextLine();
        System.out.print("enter product price :");
        product.price = scanner.nextInt();
        System.out.print("enter product quanitity :");
        product.quantity = scanner.nextInt();
        System.out.print("enter product description :");
        scanner.nextLine();
        product.description = scanner.nextLine();
        return product;
    }

    public void updateProduct(Product product) throws Exception {
        statement.executeUpdate(product.update(tableName));

    }

    public Product getProductByName(String name) {
        String sql = String.format("select * from  %s where name = '%s' ;", tableName, name);
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return deserialize(resultSet);
            } else {
                Product product = new Product();
                product.id = -1;
                return product;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            Product product = new Product();
            product.id = -1;
            return product;
        }

    }

    public Product getProductByID(int id) {
        String sql = String.format("select * from  %s where id = %d ;", tableName, id);
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return deserialize(resultSet);
            } else {
                Product product = new Product();
                product.id = -1;
                return product;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            Product product = new Product();
            product.id = -1;
            return product;
        }

    }

    public ArrayList<Product> searchProductByName(String name) throws Exception {
        ArrayList<Product> products = new ArrayList<Product>();
        String sql = String.format("select * from  %s where name like ", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sql + "CONCAT( '%',?,'%')");
        preparedStatement.setString(1, name);
        ResultSet resultSet;

        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            do {
                products.add(deserialize(resultSet));
            } while (resultSet.next());
        } else {
            System.out.println("No product found in the given name..");
        }
        return products;

    }

    public void outOfStocks() throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Product where quantity = ?");
        preparedStatement.setInt(1, 0);
        ResultSet resultSet;

        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            System.out.println("Products with no stock:");
            do {
                System.out.println(deserialize(resultSet).name);
            } while (resultSet.next());
        } else {
            System.out.println("Currnet no product is in out of stock..");
        }

    }

}
