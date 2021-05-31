package com.webapp;

import java.sql.*;
import java.util.Scanner;

class StaffHandler extends DBHandler {
    static final String tableName = "Staff";
    Scanner scanner = new Scanner(System.in);

    StaffHandler() throws Exception {
        createTableIfNotExists();
    }

    public void createTableIfNotExists() throws Exception {
        String sql = "create table if not exists Staff(id int(11) not null AUTO_INCREMENT,name varchar(120) not null,password varchar(240),primary key (id),unique (name) ) ";
        statement.execute(sql);

    }

    public Boolean insertStaff(Staff staff) {
        try {
            statement.executeUpdate(staff.save(tableName));

        } catch (Exception e) {
            System.out.println("username already exists..");
            return false;
        }
        return true;
    }

    Staff deserialize(ResultSet resultSet) throws Exception {
        Staff staff = new Staff();
        staff.name = resultSet.getString("name");
        staff.id = resultSet.getInt("id");
        return staff;
    }

    public Staff auth(Staff staff) throws Exception {
        String sql = String.format("select * from %s where name = '%s' and password = '%s' ;", tableName, staff.name,
                staff.password);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next())
            return deserialize(resultSet);
        else {

            return null;

        }
    }


    public Staff create(Staff createdStaff) throws Exception {
        if (insertStaff(createdStaff)) {
            return auth(createdStaff);

        } else {
            Staff noStaff = new Staff();
            noStaff.id = -1;
            return noStaff;
        }

    }



    public void remove(Staff staff) throws Exception {
        String sql = String.format("delete from %s where id = '%s';", tableName, staff.id);
        System.out.println(sql);
        statement.executeUpdate(sql);
    }

    public Staff getStaffByID(int id) {
        String sql = String.format("select * from  %s where id = %d ;", tableName, id);
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return deserialize(resultSet);
            } else {
                Staff staff = new Staff();
                staff.id = -1;
                return staff;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            Staff staff = new Staff();
            staff.id = -1;
            return staff;
        }

    }

}