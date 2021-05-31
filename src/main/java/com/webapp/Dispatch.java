package com.webapp;

public class Dispatch {
   public int id;
   public Staff staff;
   public Product product;
   public int quantity;
   public String timestamp;

    Dispatch(Staff staff, Product product, int quantity) {
        this.staff = staff;
        this.product = product;
        this.quantity = quantity;

    }

    public String save(String tableName) {
        String sql = String.format("insert into  %s (staffID,productID,quantity)values(%d,%d,%d) ;", tableName,
                this.staff.id, this.product.id, this.quantity);
        return sql;
    }

    @Override
    public String toString() {
        String result = String.format("ID %d , dispatched by %s , product %s , quantity %d , time %s\n", this.id,
                this.staff.name, this.product.name, this.quantity, this.timestamp);
        return result;
    }

}
