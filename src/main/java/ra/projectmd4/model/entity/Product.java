package ra.projectmd4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "sku", columnDefinition = "varchar(100)", unique = true)
    private String sku;

    @Column(name = "product_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String productName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price", columnDefinition = "decimal(10,2)")
    private BigDecimal price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "image", columnDefinition = "varchar(255)")
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @Column(name = "created_at", columnDefinition = "date")
//    private Date createdAt;
//
//    @Column(name = "updated_at", columnDefinition = "date")
//    private Date updatedAt;

    @Column(name = "created_at", columnDefinition = "timestamp")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp")
    private Timestamp updatedAt;

    public void update() {
//        this.updatedAt = Date.valueOf(LocalDate.now());
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

}
