// ================== Project Structure ==================
// src/main/java/com/inventory/entity/Product.java
// src/main/java/com/inventory/util/HibernateUtil.java
// src/main/java/com/inventory/dao/ProductDAO.java
// src/main/java/com/inventory/App.java
// src/main/resources/hibernate.cfg.xml
// pom.xml
// .gitignore

// ================== Product Entity ==================
package com.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // change to AUTO / SEQUENCE
    private int id;

    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product() {}

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

// ================== Hibernate Util ==================
package com.inventory.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

// ================== Product DAO ==================
package com.inventory.dao;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductDAO {

    // Create
    public void saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(product);
        tx.commit();
        session.close();
    }

    // Read
    public Product getProduct(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = session.get(Product.class, id);
        session.close();
        return product;
    }

    // Update
    public void updateProduct(int id, double price, int quantity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product product = session.get(Product.class, id);
        if (product != null) {
            product.setPrice(price);
            product.setQuantity(quantity);
            session.update(product);
        }

        tx.commit();
        session.close();
    }

    // Delete
    public void deleteProduct(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product product = session.get(Product.class, id);
        if (product != null) {
            session.delete(product);
        }

        tx.commit();
        session.close();
    }
}

// ================== Main Application ==================
package com.inventory;

import com.inventory.dao.ProductDAO;
import com.inventory.entity.Product;

public class App {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();

        // Insert
        dao.saveProduct(new Product("Laptop", "Gaming Laptop", 75000, 10));
        dao.saveProduct(new Product("Phone", "Smartphone", 25000, 20));

        // Read
        Product p = dao.getProduct(1);
        System.out.println(p.getName() + " " + p.getPrice());

        // Update
        dao.updateProduct(1, 70000, 8);

        // Delete
        dao.deleteProduct(2);
    }
}

// ================== hibernate.cfg.xml ==================
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/inventory_db</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">password</property>

        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="hibernate.show_sql">true</property>

        <mapping class="com.inventory.entity.Product"/>
    </session-factory>
</hibernate-configuration>

// ================== pom.xml ==================
<project xmlns="http://maven.apache.org/POM/4.0.0">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.inventory</groupId>
  <artifactId>HibernateCRUD</artifactId>
  <version>1.0</version>

  <dependencies>
    <dependency>
      <groupId>org.hibernate.orm</groupId>
      <artifactId>hibernate-core</artifactId>
      <version>6.4.0.Final</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.33</version>
    </dependency>

    <dependency>
      <groupId>jakarta.persistence</groupId>
      <artifactId>jakarta.persistence-api</artifactId>
      <version>3.1.0</version>
    </dependency>
  </dependencies>
</project>

// ================== .gitignore ==================
/target/
*.log
*.class
*.jar
*.war
*.ear
.idea/
*.iml
.DS_Store
