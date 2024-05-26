import com.felipeagomes.products.Product;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class original {
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
}
