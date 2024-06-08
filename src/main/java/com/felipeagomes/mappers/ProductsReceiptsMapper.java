package com.felipeagomes.mappers;

import com.felipeagomes.dtos.ProductsReceiptsDto;
import com.felipeagomes.dtos.ProductsReceiptsWithTotValueDto;
import com.felipeagomes.dtos.interfaces.ProductsReceiptsDtoInterface;
import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.products.Product;
import com.felipeagomes.receipts.interfaces.ReceiptReader;
import com.felipeagomes.reports.excel.DataCell;
import com.felipeagomes.reports.excel.DataRow;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductsReceiptsMapper {
    public <T> DataRow toDataRow(T dto) {
        List<DataCell> dataCells = new ArrayList<>();

        if (dto instanceof ProductsReceiptsDtoInterface productsReceiptsDtoInterface) {
            dataCells.add(new DataCell(productsReceiptsDtoInterface.code(), CellType.STRING, "Código"));
            dataCells.add(new DataCell(productsReceiptsDtoInterface.productName(), CellType.STRING, "Produto"));
            dataCells.add(new DataCell(productsReceiptsDtoInterface.purchaseDate(), CellType.STRING, "Data da Compra"));
            dataCells.add(new DataCell(productsReceiptsDtoInterface.unit(), CellType.STRING, "Unidade"));
            dataCells.add(new DataCell(productsReceiptsDtoInterface.quantity(), CellType.NUMERIC, "Quantidade"));
            dataCells.add(new DataCell(productsReceiptsDtoInterface.value(), CellType.NUMERIC, "Valor Unitário"));
            dataCells.add(new DataCell(productsReceiptsDtoInterface.superMarket(), CellType.STRING, "Mercado"));
        }

        if (dto instanceof ProductsReceiptsWithTotValueDto productsReceiptsWithTotValueDto) {
            dataCells.add(new DataCell(productsReceiptsWithTotValueDto.totValue(), CellType.NUMERIC, "Valor Total"));
        }

        return new DataRow(dataCells);
    }

    @SuppressWarnings("unchecked")
    public <T> T toDto(ProductsReceiptsDto productsReceiptsDto, Class<T> structure) {
        if (structure.equals(ProductsReceiptsWithTotValueDto.class)) {
            BigDecimal totValue = productsReceiptsDto.value().multiply(BigDecimal.valueOf(productsReceiptsDto.quantity()));

            return (T) new ProductsReceiptsWithTotValueDto(
                    productsReceiptsDto.code(),
                    productsReceiptsDto.productName(),
                    productsReceiptsDto.quantity(),
                    productsReceiptsDto.value(),
                    totValue,
                    productsReceiptsDto.unit(),
                    productsReceiptsDto.purchaseDate(),
                    productsReceiptsDto.superMarket()
            );
        } else {
            return (T) productsReceiptsDto;
        }
    }

    public List<ProductsReceipts> receiptReaderToProductsReceipts(ReceiptReader receiptReader) {
        List<ProductsReceipts> productsReceipts = new ArrayList<>();
        for (Product product : receiptReader.getProducts()) {
            BigDecimal valor = BigDecimal.valueOf(product.getValor());
            ProductsReceipts productReceipt = new ProductsReceipts(product.getCodigo(), product.getNome(), product.getQuantidade(), product.getUnidade(), valor);
            productReceipt.setSupermarket(receiptReader.getCompanyName());
            productReceipt.setPurchaseDate(receiptReader.getDate());

            productsReceipts.add(productReceipt);
        }

        return productsReceipts;
    }
}
