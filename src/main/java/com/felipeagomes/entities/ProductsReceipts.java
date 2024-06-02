package com.felipeagomes.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products_receipts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "purchase_date"})
})
@NamedQueries({
        @NamedQuery(
                name = "ProductsReceipts.findAll",
                query = """
                SELECT
                     new com.felipeagomes.dtos.ProductsReceiptsDto(
                                         e.code,
                                         e.productName,
                                         e.quantity,
                                         e.value,
                                         e.unit,
                                         e.purchaseDate,
                                         e.supermarket)
                FROM
                    ProductsReceipts e"""
        )
})
public class ProductsReceipts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "product_name", length = 512, nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "unit", length = 2, nullable = false)
    private String unit;

    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "supermarket", nullable = false)
    private String supermarket;

    public ProductsReceipts() {}

    public ProductsReceipts(String code, String productName, Double quantity, String unit, BigDecimal value) {
        this.code = code;
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }
}
