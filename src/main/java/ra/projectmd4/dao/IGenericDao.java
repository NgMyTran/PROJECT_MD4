package ra.projectmd4.dao;

import java.util.List;

public interface IGenericDao <T,E>{
    List<T> findAll();
    T findById(int id);
    void create(T t);
    void update(T t);
    void delete(T t);
}
