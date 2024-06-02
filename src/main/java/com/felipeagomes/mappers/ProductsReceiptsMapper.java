package com.felipeagomes.mappers;

import com.felipeagomes.dtos.ProductsReceiptsDto;
import com.felipeagomes.entities.ProductsReceipts;
import com.felipeagomes.exceptions.InvalidStructureToMapDtoReceiptReaderException;
import com.felipeagomes.exceptions.ReceiptReaderException;
import com.felipeagomes.reports.excel.DataCell;
import com.felipeagomes.reports.excel.DataRow;
import org.apache.poi.ss.usermodel.CellType;

import java.util.ArrayList;
import java.util.List;

public class ProductsReceiptsMapper implements MapperService<ProductsReceiptsDto> {
    public ProductsReceiptsDto toProductsReceiptsDto(ProductsReceipts productsReceipts) {
        return new ProductsReceiptsDto(
                productsReceipts.getCode(), productsReceipts.getProductName(), productsReceipts.getQuantity(), productsReceipts.getValue(), productsReceipts.getUnit(),
                productsReceipts.getPurchaseDate(), productsReceipts.getSupermarket()
        );
    }

    @Override
    public DataRow toDataRow(ProductsReceiptsDto dto) {
        List<DataCell> dataCells = new ArrayList<>();

        dataCells.add(new DataCell(dto.code(), CellType.STRING, "Código"));
        dataCells.add(new DataCell(dto.productName(), CellType.STRING, "Produto"));
        dataCells.add(new DataCell(dto.purchaseDate(), CellType.STRING, "Data da Compra"));
        dataCells.add(new DataCell(dto.unit(), CellType.STRING, "Unidade"));
        dataCells.add(new DataCell(dto.quantity(), CellType.NUMERIC, "Quantidade"));
        dataCells.add(new DataCell(dto.value(), CellType.NUMERIC, "Valor"));
        dataCells.add(new DataCell(dto.superMarket(), CellType.STRING, "Mercado"));

        return new DataRow(dataCells);
    }
}
