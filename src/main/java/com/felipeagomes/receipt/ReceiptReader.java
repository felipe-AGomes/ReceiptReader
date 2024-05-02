package com.felipeagomes.receipt;

import com.felipeagomes.products.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptReader {
    private String content;
    private List<Product> products = new ArrayList<>();

    public ReceiptReader(File receiptFile) {
        readReceipt(receiptFile);
    }

    private void readReceipt(File receiptFile) {
        this.content = extractContentFromReceipt(receiptFile);
        this.products = extractProductsFromContent();
    }

    private String extractContentFromReceipt(File receiptFile) {
        try {
            PDDocument pdDocument = Loader.loadPDF(receiptFile);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(pdDocument);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    private List<Product> extractProductsFromContent() {
        List<Product> products = new ArrayList<>();
        String[] lines = content.split("\n");

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("Qtde.")) {
                Product product = createProductFromLines(lines, i);
                products.add(product);
            }
        }
        return products;
    }

    private Product createProductFromLines(String[] lines, int index) {
        Product product = new Product();
        setProductInfo(product, lines, index);
        setProductQuantityUnitValue(product, lines[index]);
        return product;
    }

    private void setProductInfo(Product product, String[] lines, int index) {
        String productName;
        String productCode;
        Pattern codigoPattern = Pattern.compile("^\\({0,1}\\s*(?:Código: ){0,1}[0-9]*\\s*\\)");
        Matcher codigoMatcher = codigoPattern.matcher(lines[index - 1]);

        if (codigoMatcher.find()) {
            productName = extractProductName(lines[index - 2]);
            productCode = extractProductCode(lines[index - 1]);
        } else {
            productName = extractProductName(lines[index - 1]);
            productCode = extractProductCode(lines[index - 1]);
        }

        product.setNome(productName);
        product.setCodigo(productCode);
    }

    private void setProductQuantityUnitValue(Product product, String line) {
        product.setQuantidade(extractPatternFromLine("Qtde\\.:([0-9,]*)", line));
        product.setUnidade(extractPatternFromLine("UN:\\s([A-Za-z]*)", line));
        product.setValor(extractPatternFromLine("Unit\\.:   ([0-9,]*)", line));
    }

    private String extractProductName(String inputString) {
        return extractPatternFromLine("^(.*?)(?=\\s*\\(Código:|$)", inputString);
    }

    private String extractProductCode(String inputString) {
        return extractPatternFromLine("([0-9]*)\\s\\)", inputString);
    }

    private String extractPatternFromLine(String pattern, String line) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public String getContent() {
        return content;
    }

    public List<Product> getProducts() {
        return products;
    }
}
