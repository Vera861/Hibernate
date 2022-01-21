package ru.gb;

import org.hibernate.cfg.Configuration;
import ru.gb.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

//         INSERT
//        em.getTransaction().begin();
//
//        em.persist(new Product(null, "apple", 15));
//        em.persist(new Product(null, "apricot", 19));
//        em.persist(new Product(null, "orange", 15));
//
//        em.getTransaction().commit();

        // SELECT
//        System.out.println("Products:");
//        Product product = em.find(Product.class, 1L);
//        System.out.println(product);
//
//
//        System.out.println("Products:");
//        List<Product> prod = em.createQuery("select p from Product p where p.title = :title", Product.class)
//                .setParameter("title", "orange")
//                .getResultList();
//        System.out.println(prod);
//
//        prod = em.createNativeQuery("select * from products", Product.class)
//                .getResultList();
//        System.out.println(prod);

        // UPDATE 1
//        Product product = em.find(Product.class, 1L);
//
//        em.getTransaction().begin();
//
//        product.setPrice(25);
//
//        em.getTransaction().commit();

//         UPDATE 2
//        Product product = new Product(1L, "kiwi", 14);
//
//        em.getTransaction().begin();
//
//        em.merge(product);
//
//        em.getTransaction().commit();


//         UPDATE 3
//        em.getTransaction().begin();
//
//        em.createQuery("update Product set title = :title, price = :price where id = :id")
//                .setParameter("title", "red apple")
//                .setParameter("price", 13)
//                .setParameter("id", 1L)
//                .executeUpdate();
//
//        em.getTransaction().commit();


        em.close();
    }
}
