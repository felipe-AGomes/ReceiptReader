package com.felipeagomes.exceptions;

public class ProductsReceiptsAlreadyExistsException extends ReceiptReaderException {
    public ProductsReceiptsAlreadyExistsException() {
        super("Products receipts already exists");
    }
}
