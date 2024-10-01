package ra.projectmd4.dao.user;

import ra.projectmd4.model.entity.User;

import java.util.List;

public interface IUserDao {
    User findByUsername(String username);
    void register(User user);
    void block(Long id);
    void unBlock(Long id);
    void delete(Long id);
    User findById(Long id);

    List<User> getListUser(String keyword, int page, int pageSize);
    List<User> searchUser(String keyword);
    List<User> paginate(int page, int pageSize);
    long getTotalElements(String keyword);
}
