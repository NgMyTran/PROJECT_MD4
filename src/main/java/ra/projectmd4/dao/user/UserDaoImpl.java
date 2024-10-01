package ra.projectmd4.dao.user;

import org.springframework.stereotype.Repository;
import ra.projectmd4.model.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements IUserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("from User where username=:username and isDeleted = false", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
//        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void register(User user) {
        em.persist(user);
    }

    @Override
    public List<User> getListUser(String keyword, int page, int pageSize) {
        TypedQuery<User> query= em.createQuery("from User where isDeleted = false and (fullName like :keyword or username like :keyword or email like :keyword)", User.class);
    query.setParameter("keyword", "%"+keyword+"%");
    query.setFirstResult(page * pageSize).setMaxResults(pageSize);
    return query.getResultList();
    }

    @Override
    public List<User> searchUser(String keyword) {
        TypedQuery<User> query = em.createQuery("from User where isDeleted = false and (fullName like :keyword or username like :keyword or email like :keyword)", User.class);
        query.setParameter("keyword", "%"+keyword+"%");
        return query.getResultList();
    }

    @Override
    public List<User> paginate(int page, int pageSize) {
        TypedQuery<User> query = em.createQuery("from User where isDeleted = false", User.class);
        query.setFirstResult(page*pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long getTotalElements(String keyword) {
        TypedQuery<Long> count = em.createQuery("select count(U) from User U where isDeleted = false and (fullName like :keyword or username like :keyword or email like :keyword)", Long.class);
        count.setParameter("keyword", "%"+keyword+"%");
        return count.getSingleResult();
    }

    @Override
    public User findById(Long id) {
        TypedQuery<User> query = em.createQuery("from User where id=:id", User.class);
        User u = query.setParameter("id", id).getSingleResult();
        return u;
    }

    @Override
@Transactional
    public void delete(Long id) {
        User userDel=findById(id);
        if(userDel!=null){
            userDel.setDeleted(true);
            em.merge(userDel);
        }
    }

    @Override
    @Transactional
    public void block(Long id) {
        User userBlock=findById(id);
        if(userBlock!=null){
            userBlock.setStatus(false);
            em.merge(userBlock);
        }

    }

    @Override
    @Transactional
    public void unBlock(Long id) {
        User userBlocked=findById(id);
        if(userBlocked!=null){
            userBlocked.setStatus(true);
            em.merge(userBlocked);
        }
    }
}
