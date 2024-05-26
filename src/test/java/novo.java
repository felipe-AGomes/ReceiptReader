import com.felipeagomes.products.Product;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class novo {
    private List<String> contentLines;

    private Product createProductFromLines(int index) {
        Product product = new Product();

        product.setNome(getProductName(index));
        product.setCodigo(getProductCodigo(index));

        product.setUnidade(getProductUnidade(index));
        product.setQuantidade(getProductQuantidade(index));
        product.setValor(getProductValor(index));

        return product;
    }

    private Double getProductValor(int index) {
        String line = contentLines.get(index);
        String valorString = extractPatternFromLine("Unit\\.:   ([0-9,]*)", line);
        return Double.parseDouble(valorString.replace(".", "").replace(",", "."));
    }

    private Double getProductQuantidade(int index) {
        String line = contentLines.get(index);
        String quantidadeString = extractPatternFromLine("Qtde\\.:([0-9,]*)", line).replace(".", "").replace(",", ".");
        return Double.parseDouble(quantidadeString);
    }

    private String getProductUnidade(int index) {
        String line = contentLines.get(index);
        return extractPatternFromLine("UN:\\s([A-Za-z]*)", line);
    }

    private String getProductCodigo(int index) {
        String previousLine = this.contentLines.get(index - 1);
        String productCode;

        productCode = extractProductCode(previousLine);

        return productCode;
    }

    private String getProductName(int index) {
        String productName;
        String lineBeforePrevious = index > 1 ? contentLines.get(index - 2) : "";
        String previousLine = this.contentLines.get(index - 1);

        Pattern codigoPattern = Pattern.compile("^\\({0,1}\\s*(?:Código: ){0,1}[0-9]*\\s*\\)");
        Matcher codigoMatcher = codigoPattern.matcher(previousLine);

        if (codigoMatcher.find()) {
            productName = extractProductName(lineBeforePrevious);
        } else {
            productName = extractProductName(previousLine);
        }

        return productName;
    }









    private String extractProductName(String lineBeforePrevious) {
        return null;
    }

    private String extractPatternFromLine(String s, String line) {
        return null;
    }

    private String extractProductCode(String previousLine) {
        return null;
    }
}
