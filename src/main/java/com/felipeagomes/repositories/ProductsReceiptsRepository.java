package com.felipeagomes.repositories;

import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.exceptions.ProductsReceiptsAlreadyExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ProductsReceiptsRepository {
    public void saveAll(List<ProductsReceipts> productsReceipts) {
        EntityTransaction tr = null;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReceipReader");
             EntityManager em = emf.createEntityManager()) {

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
}
