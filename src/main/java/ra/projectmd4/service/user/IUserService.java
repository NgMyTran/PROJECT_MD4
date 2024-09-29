package ra.projectmd4.service.user;

import ra.projectmd4.model.dto.request.FormLogin;
import ra.projectmd4.model.dto.request.FormRegister;
import ra.projectmd4.model.dto.response.UserInfo;

public interface IUserService {
    UserInfo login(FormLogin request);
    void register(FormRegister request);
}
