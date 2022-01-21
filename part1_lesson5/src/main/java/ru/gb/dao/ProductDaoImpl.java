package ru.gb.dao;

import ru.gb.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductDaoImpl implements ProductDao {

    private final EntityManagerFactory emFactory;

    public ProductDaoImpl(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public List<Product> findAll() {
        return executeForEntityManager(
                em -> em.createQuery("from Product p", Product.class)
                        .getResultList()
        );
    }

    @Override
    public Optional<Product> findById(long id) {
        return executeForEntityManager(
                em -> Optional.ofNullable(em.find(Product.class, id))
        );
    }

    @Override
    public void saveOrUpdate(Product product) {
        executeInTransaction(em -> {
            if (product.getId() == null) {
                em.persist(product);
            } else {
                em.merge(product);
            }
        });
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
