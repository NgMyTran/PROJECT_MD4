package ra.projectmd4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // ID cho chi tiết đơn hàng

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Khóa ngoại liên kết với đơn hàng

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Khóa ngoại liên kết với sản phẩm

    @Column(name = "name", nullable = false)
    private String name; // Tên sản phẩm

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice; // Đơn giá

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity; // Số lượng đặt hàng

    // Phương thức hỗ trợ có thể thêm ở đây, nếu cần
}
