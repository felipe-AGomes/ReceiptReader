package com.felipeagomes.receipts;

import com.felipeagomes.products.Product;
import com.felipeagomes.receipts.interfaces.ReceiptReader;
import com.felipeagomes.utils.DateUtil;
import com.felipeagomes.utils.StringUtil;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFReceiptReader implements ReceiptReader {

    private List<String> contentLines;
    private List<Product> products = new ArrayList<>();
    private String companyName;
    private Date date;
    private String title;

    public ReceiptReader readReceipt(File receiptFile) {
        this.contentLines = extractContentFromReceipt(receiptFile);
        this.products = extractProductsFromContent();
        this.companyName = extractCompanyNameFromContent();
        this.date = extractDateFromContent();
        setTitle();

        return this;
    }

    private List<String> extractContentFromReceipt(File receiptFile) {
        try (PDDocument pdDocument = Loader.loadPDF(receiptFile)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return Arrays.asList(pdfTextStripper.getText(pdDocument).split("\n"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String extractCompanyNameFromContent() {
        for (int i = 0; i < contentLines.size(); i++) {
            int previousLine = i - 1;
            String line = contentLines.get(i);
            if (line.startsWith("CNPJ:")) {
                return contentLines.get(previousLine);
            }
        }
        return "";
    }

    private Date extractDateFromContent() {
        String dateLine = extractDateLineFromContent();
        String dateString = extractDateFromDateLine(dateLine);
        return DateUtil.toDate(dateString);
    }

    private String extractDateLineFromContent() {
        for (String line : contentLines) {
            if (line.startsWith("EMISSÃO")) {
                int index = contentLines.indexOf(line) + 1;
                return contentLines.get(index);
            }
        }
        return null;
    }

    private String extractDateFromDateLine(String dateLine) {
        if (dateLine == null) return null;
        Pattern datePattern = Pattern.compile(".*Emissão: (\\d\\d/\\d\\d/\\d\\d\\d\\d\\s\\d\\d:\\d\\d:\\d\\d)");
        Matcher dateMatcher = datePattern.matcher(dateLine);
        return dateMatcher.find() ? dateMatcher.group(1) : null;
    }

    private List<Product> extractProductsFromContent() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < contentLines.size(); i++) {
            String line = contentLines.get(i);
            if (line.startsWith("Qtde.")) {
                Product product = createProductFromLines(i);
                products.add(product);
            }
        }
        return products;
    }

    private Product createProductFromLines(int index) {
        String line = this.contentLines.get(index);
        String previousLine = this.contentLines.get(index - 1);
        String beforePreviousLine = index > 1 ? contentLines.get(index - 2) : "";

        Product product = new Product();
        product.setNome(getProductName(previousLine, beforePreviousLine));
        product.setCodigo(getProductCodigo(previousLine));
        product.setUnidade(getProductUnidade(line));
        product.setQuantidade(getProductQuantidade(line));
        product.setValor(getProductValor(line));

        return product;
    }

    private Double getProductValor(String line) {
        return Double.parseDouble(extractPatternFromLine("Unit\\.:   ([0-9,]*)", line).replace(".", "").replace(",", "."));
    }

    private Double getProductQuantidade(String line) {
        return Double.parseDouble(extractPatternFromLine("Qtde\\.:([0-9,]*)", line).replace(".", "").replace(",", "."));
    }

    private String getProductUnidade(String line) {
        return extractPatternFromLine("UN:\\s([A-Za-z]*)", line);
    }

    private String getProductCodigo(String previousLine) {
        return extractProductCode(previousLine);
    }

    private String getProductName(String previousLine, String lineBeforePrevious) {
        Pattern codigoPattern = Pattern.compile("^\\({0,1}\\s*(?:Código: ){0,1}[0-9]*\\s*\\)");
        Matcher codigoMatcher = codigoPattern.matcher(previousLine);
        if (codigoMatcher.find()) {
            return extractProductName(lineBeforePrevious);
        } else {
            return extractProductName(previousLine);
        }
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
        return matcher.find() ? matcher.group(1) : "";
    }

    public List<String> getContentLines() {
        return contentLines;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle() {
        final String DESIRED_FORMAT = "dd_MM_yyyy";
        String formattedCompanyName = StringUtil.formatCompanyName(companyName);
        String formattedDate = DateUtil.toString(date, DESIRED_FORMAT);
        title = (formattedCompanyName.trim() + " " + formattedDate.trim()).trim();
    }

    public Receipt getReceipt() {
        return new Receipt(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
