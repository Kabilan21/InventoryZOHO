package com.webapp;

public class Staff {
    int id;
   public String name;
   public String password;

    Staff(String name, String password) {
        this.name = name;
        this.password = password;
    }

    Staff() {
    }

    public String save(String tableName) {
        String sql = String.format("insert into  %s (name,password)values('%s','%s');", tableName, this.name,
                this.password);
        return sql;
    }

    @Override
    public String toString() {
        String result = String.format("ID : %d \nName : %s", this.id, this.name);
        return result;
    }

}
