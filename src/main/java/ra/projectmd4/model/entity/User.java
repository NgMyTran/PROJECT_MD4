package ra.projectmd4.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(length = 100)
    private String username;
    @Column(unique = true)
    private String email;
    private String fullName;
    private String password;
    private String avatar;
    private String phone;
    private String address;
    private boolean status;
    private Date createdAt;
    private Date updateAt;
    private boolean isDeleted;
    private boolean role;
}