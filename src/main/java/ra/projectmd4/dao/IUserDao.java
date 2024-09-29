package ra.projectmd4.dao;

import ra.projectmd4.model.entity.User;

public interface IUserDao {
    User findByUsername(String username);
    void register(User user);
}
