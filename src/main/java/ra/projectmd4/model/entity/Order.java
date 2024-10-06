//package ra.projectmd4.model.entity;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
//@Entity
//@Table(name = "orders")
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_id")
//    private Long orderId;
//
//    @Column(name = "serial_number", nullable = false, unique = true)
//    private String serialNumber;
//
//    @Column(name = "user_id", nullable = false)
//    private Long userId;
//
//    @Column(name = "total_price", nullable = false)
//    private BigDecimal totalPrice;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private OrderStatus status;
//
//    @Column(name = "note")
//    private String note;
//
//    @Column(name = "receive_name")
//    private String receiveName;
//
//    @Column(name = "receive_address")
//    private String receiveAddress;
//
//    @Column(name = "receive_phone")
//    private String receivePhone;
//
//    @Column(name = "created_at", columnDefinition = "DATE DEFAULT CURRENT_DATE()")
//    private LocalDate createdAt;
//
//    @Column(name = "received_at")
//    private LocalDate receivedAt;
//}