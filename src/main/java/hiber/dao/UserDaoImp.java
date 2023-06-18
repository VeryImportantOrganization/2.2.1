package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        if (user.getCar() != null) {
            sessionFactory.getCurrentSession().save(user.getCar());
        }
        sessionFactory.getCurrentSession().save(user);
    }



    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserWithCar(String model, int series) {
        String HQL = "    SELECT users\n" +
                "              FROM User users\n" +
                "              JOIN users.car car\n" +
                "              WHERE car.model = :model AND car.series = :series";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(HQL, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        return query.getSingleResult();
    }
}
