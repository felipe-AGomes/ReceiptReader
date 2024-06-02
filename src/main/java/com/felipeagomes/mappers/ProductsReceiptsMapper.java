package com.felipeagomes.mappers;

import com.felipeagomes.dtos.ProductsReceiptsDto;
import com.felipeagomes.dtos.ProductsReceiptsWithTotValueDto;
import com.felipeagomes.dtos.interfaces.ProductsReceiptsDtoInterface;
import com.felipeagomes.reports.excel.DataCell;
import com.felipeagomes.reports.excel.DataRow;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductsReceiptsMapper {
    public <T extends ProductsReceiptsDtoInterface> DataRow toDataRow(T dto) {
        List<DataCell> dataCells = new ArrayList<>();

        dataCells.add(new DataCell(dto.code(), CellType.STRING, "Código"));
        dataCells.add(new DataCell(dto.productName(), CellType.STRING, "Produto"));
        dataCells.add(new DataCell(dto.purchaseDate(), CellType.STRING, "Data da Compra"));
        dataCells.add(new DataCell(dto.unit(), CellType.STRING, "Unidade"));
        dataCells.add(new DataCell(dto.quantity(), CellType.NUMERIC, "Quantidade"));
        dataCells.add(new DataCell(dto.value(), CellType.NUMERIC, "Valor Unitário"));
        dataCells.add(new DataCell(dto.superMarket(), CellType.STRING, "Mercado"));

        if (dto instanceof ProductsReceiptsWithTotValueDto productsReceiptsWithTotValueDto) {
            dataCells.add(new DataCell(productsReceiptsWithTotValueDto.totValue(), CellType.NUMERIC, "Valor Total"));
        }

        return new DataRow(dataCells);
    }

    @SuppressWarnings("unchecked")
    public <T extends ProductsReceiptsDtoInterface> T toDto(ProductsReceiptsDto productsReceiptsDto, Class<T> structure) {
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
}
