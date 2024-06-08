package com.felipeagomes.repositories;

import com.felipeagomes.dtos.ProductsReceiptsDto;
import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.exceptions.ProductsReceiptsAlreadyExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.hibernate.exception.ConstraintViolationException;

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
        } catch (ConstraintViolationException e) {
            throw new ProductsReceiptsAlreadyExistsException();
        } catch (Exception e) {
            e.printStackTrace();

            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
        }
    }

    public List<ProductsReceiptsDto> executeQueryAndGetResultList(String namedQuery) {
        try (EntityManager em = emf.createEntityManager()) {
            Query query = em.createNamedQuery(namedQuery, ProductsReceiptsDto.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
