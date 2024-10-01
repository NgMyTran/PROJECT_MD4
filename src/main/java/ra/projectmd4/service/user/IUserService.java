package ra.projectmd4.service.user;

import ra.projectmd4.model.dto.request.FormLogin;
import ra.projectmd4.model.dto.request.FormRegister;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.dto.response.UserResponse;
import ra.projectmd4.model.entity.User;

import java.util.List;


public interface IUserService {
    UserInfo login(FormLogin request);
    void register(FormRegister request);
    void block(Long id);
    void unBlock(Long id);
    void delete(Long id);
    User findById(Long id);

    List<UserResponse> getListUsers(String key, int page, int size);
    List<UserResponse> searchUsers(String search);
    List<UserResponse> paginate(int page, int size);
    long getTotalElements(String key);
}
