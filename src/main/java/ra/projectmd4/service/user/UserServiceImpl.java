package ra.projectmd4.service.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectmd4.dao.user.IUserDao;
import ra.projectmd4.exception.AuthenticationException;
import ra.projectmd4.model.dto.request.FormLogin;
import ra.projectmd4.model.dto.request.FormRegister;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.dto.response.UserResponse;
import ra.projectmd4.model.entity.User;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public UserInfo login(FormLogin request) {
       try {
           User user = userDao.findByUsername(request.getUsername());
           if (!user.isStatus()){
               throw new AuthenticationException("Your account is blocked. Please contact support.");
           }
           boolean isSuccess=BCrypt.checkpw(request.getPassword(), user.getPassword());
           if(isSuccess){
               return new UserInfo(user);
           }
           throw new AuthenticationException("Username or pass incorrect");
       }
       catch (NoResultException e) {
           throw new AuthenticationException("Username or pass incorrect");
       }
    }


    @Override
    @Transactional
    public void register(FormRegister request) {
        User user = User.builder()
                .username(request.getUsername())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt(5)))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(false)
                .avatar("example.jpg")
                .createdAt(new Date())
                .status(true)
                .isDeleted(false)
                .updateAt(new Date())
                .build();
        userDao.register(user);
    }

    @Override
    public List<UserResponse> getListUsers(String key, int page, int size) {
        return userDao.getListUser(key,page,size).stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> searchUsers(String search) {
        return userDao.searchUser(search).stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> paginate(int page, int size) {
        return userDao.paginate(page,size).stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Override
    public long getTotalElements(String key) {
        return userDao.getTotalElements(key);

    }

    @Override
    @Transactional
    public void block(Long id) {
        User userToBlock = userDao.findById(id);
        // Check if the user to block is an admin
        if (userToBlock != null && userToBlock.isRole() == false) { // Assuming isRole() returns a boolean
            userDao.block(id);
        } else {
            throw new AuthenticationException("Admin cannot be blocked.");
        }
    }

    @Override
    @Transactional
    public void unBlock(Long id) {
        userDao.unBlock(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userDao.findById(id);
    }
}
