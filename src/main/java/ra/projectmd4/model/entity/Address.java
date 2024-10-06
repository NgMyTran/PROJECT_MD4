package ra.projectmd4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "receive_name")
    private String receiveName;
}
