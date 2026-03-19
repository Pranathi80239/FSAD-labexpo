package com.inventory.hql;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class Experiment3 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Sorting by price ASC
        Query<Product> q1 = session.createQuery("FROM Product ORDER BY price ASC", Product.class);
        q1.list().forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // Sorting by price DESC
        Query<Product> q2 = session.createQuery("FROM Product ORDER BY price DESC", Product.class);
        q2.list().forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // Sort by quantity (highest first)
        Query<Product> q3 = session.createQuery("FROM Product ORDER BY quantity DESC", Product.class);
        q3.list().forEach(p -> System.out.println(p.getName() + " " + p.getQuantity()));

        // Pagination - First 3 products
        Query<Product> q4 = session.createQuery("FROM Product", Product.class);
        q4.setFirstResult(0);
        q4.setMaxResults(3);
        System.out.println("First 3 Products:");
        q4.list().forEach(p -> System.out.println(p.getName()));

        // Pagination - Next 3 products
        Query<Product> q5 = session.createQuery("FROM Product", Product.class);
        q5.setFirstResult(3);
        q5.setMaxResults(3);
        System.out.println("Next 3 Products:");
        q5.list().forEach(p -> System.out.println(p.getName()));

        // Aggregate - Count total products
        Long total = session.createQuery("SELECT COUNT(*) FROM Product", Long.class).uniqueResult();
        System.out.println("Total Products: " + total);

        // Count where quantity > 0
        Long available = session.createQuery("SELECT COUNT(*) FROM Product WHERE quantity > 0", Long.class).uniqueResult();
        System.out.println("Available Products: " + available);

        // Group by description count
        List<Object[]> groupDesc = session.createQuery(
                "SELECT description, COUNT(*) FROM Product GROUP BY description", Object[].class).list();
        groupDesc.forEach(obj -> System.out.println(obj[0] + " -> " + obj[1]));

        // Min and Max price
        Object[] minMax = session.createQuery(
                "SELECT MIN(price), MAX(price) FROM Product", Object[].class).uniqueResult();
        System.out.println("Min Price: " + minMax[0] + ", Max Price: " + minMax[1]);

        // Price range filter
        Query<Product> q6 = session.createQuery("FROM Product WHERE price BETWEEN :min AND :max", Product.class);
        q6.setParameter("min", 20000);
        q6.setParameter("max", 80000);
        q6.list().forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));

        // LIKE queries
        session.createQuery("FROM Product WHERE name LIKE 'L%'", Product.class)
                .list().forEach(p -> System.out.println("Starts with L: " + p.getName()));

        session.createQuery("FROM Product WHERE name LIKE '%e'", Product.class)
                .list().forEach(p -> System.out.println("Ends with e: " + p.getName()));

        session.createQuery("FROM Product WHERE name LIKE '%ap%'", Product.class)
                .list().forEach(p -> System.out.println("Contains 'ap': " + p.getName()));

        session.createQuery("FROM Product WHERE LENGTH(name) = 5", Product.class)
                .list().forEach(p -> System.out.println("Length 5: " + p.getName()));

        session.close();
    }
}
