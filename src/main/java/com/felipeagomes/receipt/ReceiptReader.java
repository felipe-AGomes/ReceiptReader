package com.felipeagomes.receipt;

import com.felipeagomes.products.Product;
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

public class ReceiptReader {
    private List<String> contentLines;
    private List<Product> products = new ArrayList<>();
    private String companyName;
    private Date date;
    private String title;

    public ReceiptReader(File receiptFile) {
        readReceipt(receiptFile);
    }

    private void readReceipt(File receiptFile) {
        this.contentLines = extractContentFromReceipt(receiptFile);
        this.products = extractProductsFromContent();
        this.companyName = extractCompanyNameFromContent();
        this.date = extractDateFromContent();
        setTitle();
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
        return contentLines.getFirst();
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
                return contentLines.size() > index ? contentLines.get(index) : null;
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
        for (String line : contentLines) {
            if (line.startsWith("Qtde.")) {
                int index = contentLines.indexOf(line);
                Product product = createProductFromLines(index);
                products.add(product);
            }
        }
        return products;
    }

    private Product createProductFromLines(int index) {
        Product product = new Product();
        setProductInfo(product, index);
        setProductQuantityUnitValue(product, contentLines.get(index));
        return product;
    }

    private void setProductInfo(Product product, int index) {
        String productName;
        String productCode;
        String previousLine = contentLines.get(index - 1);
        String lineBeforePrevious = index > 1 ? contentLines.get(index - 2) : "";

        Pattern codigoPattern = Pattern.compile("^\\({0,1}\\s*(?:Código: ){0,1}[0-9]*\\s*\\)");
        Matcher codigoMatcher = codigoPattern.matcher(previousLine);

        if (codigoMatcher.find()) {
            productName = extractProductName(lineBeforePrevious);
        } else {
            productName = extractProductName(previousLine);
        }
        productCode = extractProductCode(previousLine);

        product.setNome(productName);
        product.setCodigo(productCode);
    }

    private void setProductQuantityUnitValue(Product product, String line) {
        product.setUnidade(extractPatternFromLine("UN:\\s([A-Za-z]*)", line));
        String quantidadeString = extractPatternFromLine("Qtde\\.:([0-9,]*)", line).replace(".", "").replace(",", ".");
        product.setQuantidade(Double.parseDouble(quantidadeString));
        String valorString = extractPatternFromLine("Unit\\.:   ([0-9,]*)", line);
        product.setValor(Double.parseDouble(valorString.replace(".", "").replace(",", ".")));
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
}
