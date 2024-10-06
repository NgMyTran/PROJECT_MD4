package ra.projectmd4.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.projectmd4.model.entity.Product;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int stock;
    private String categoryName;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.categoryName = product.getCategory().getName();
    }
}
