package ra.projectmd4.service.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectmd4.dao.IUserDao;
import ra.projectmd4.exception.AuthenticationException;
import ra.projectmd4.model.dto.request.FormLogin;
import ra.projectmd4.model.dto.request.FormRegister;
import ra.projectmd4.model.dto.response.UserInfo;
import ra.projectmd4.model.entity.User;

import javax.persistence.NoResultException;
import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public UserInfo login(FormLogin request) {
       try {
           User user = userDao.findByUsername(request.getUsername());
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
}
