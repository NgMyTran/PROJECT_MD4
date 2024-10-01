package ra.projectmd4.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectmd4.model.entity.User;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String avatar;
    private String phone;
    private String address;
    private boolean status;
    private Date createdAt;
    private boolean role;
    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.avatar = user.getAvatar();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.status = user.isStatus();
        this.createdAt = user.getCreatedAt();
        this.role = user.isRole();
    }
}
