package ra.projectmd4.model.dto.request;

import lombok.Data;

@Data
public class FormRegister {
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;
    private String phone;
    private String address;
}
