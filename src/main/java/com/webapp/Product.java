package com.webapp;

public class Product {
   public int id;
   public String name;
   public int price;
   public int quantity;
   public String description;

    Product() {
    }

    Product(String name, int price, int quantity, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public String save(String tableName) {
        String sql = String.format("insert into  %s (name,price,quantity,description)values('%s',%d,%d,'%s');",
                tableName, this.name, this.price, this.quantity, this.description);
        return sql;
    }

    public String update(String tableName) {
        String sql = String.format(
                " update %s set name = '%s' , price = %d ,quantity = %d , description = '%s' where id = %d ", tableName,
                this.name, this.price, this.quantity, this.description, this.id);

        return sql;

    }

    @Override
    public String toString() {
        String result = String.format("ID %d , Name %s , Price %d , Quantity %d , Description %s\n", this.id, this.name,
                this.price, this.quantity, this.description);
        return result;
    }

}
