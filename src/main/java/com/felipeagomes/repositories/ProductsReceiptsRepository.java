package com.felipeagomes.repositories;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.exceptions.ProductsReceiptsAlreadyExistsException;
import jakarta.persistence.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.QueryTypeMismatchException;

import java.util.List;

public class ProductsReceiptsRepository {
    final private EntityManagerFactory emf;

    public ProductsReceiptsRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveAll(List<ProductsReceipts> productsReceipts) {
        EntityTransaction tr = null;
        try (EntityManager em = emf.createEntityManager()) {

            tr = em.getTransaction();
            tr.begin();

            for (ProductsReceipts productReceipt : productsReceipts) {
                em.persist(productReceipt);
            }

            tr.commit();
        } catch (ConstraintViolationException e){
            throw new ProductsReceiptsAlreadyExistsException();
        } catch (Exception e) {
            e.printStackTrace();

            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
        }
    }

    public <T> List<T> executeQueryAndGetResultList(String namedQuery, Class<T> structure) {
        try (EntityManager em = emf.createEntityManager()) {
            Query query = em.createNamedQuery(namedQuery, structure);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
