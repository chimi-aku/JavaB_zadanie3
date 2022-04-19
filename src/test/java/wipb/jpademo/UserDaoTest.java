package wipb.jpademo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wipb.jpademo.dao.UserDao;
import wipb.jpademo.dao.UserDaoJpaImpl;
import wipb.jpademo.model.User;

import java.util.Optional;

public class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    public void setup() {
        userDao = new UserDaoJpaImpl();
    }

    @AfterEach
    public void clear() {
        //  usuwa uzytkownikow po kazdym tescie
        for (User u:userDao.findAll())
            userDao.delete(u);
    }

    @Test
    public void testSave() {
        User u = new User(null,"abc","pass","abc@abc.pl");
        userDao.save(u);
        assert u.getId() != null;
    }

    @Test
    public void testSaveWithGroups() {
        User u = new User(null,"abc","pass","abc@abc.pl");
        u.addGroup("group1");
        u.addGroup("group2");
        userDao.save(u);

        Optional<User> o = userDao.findById(u.getId());
        assert o.isPresent();
        assert o.get().getUserGroups().size() == 2;
    }

    @Test
    public void testUpdateWithGroups() {
        User u = new User(null,"abc","pass","abc@abc.pl");
        u.addGroup("group1");
        u.addGroup("group2");
        userDao.save(u);

        u = userDao.findById(u.getId()).get();
        assert u.getUserGroups().size() == 2;

        u.getUserGroups().remove(0);
        userDao.update(u);

        u = userDao.findById(u.getId()).get();
        assert u.getUserGroups().size() == 1;
    }

    @Test
    public void testSaveUniqueLogin() {
        User u = new User(null,"abc","pass","abc@abc.pl");
        userDao.save(u);

        Assertions.assertThrows(javax.persistence.RollbackException.class, ()-> {
            User u2 = new User(null,"abc","pass2","abc2@abc.pl");
            userDao.save(u2);
        });

    }

    @Test
    public void testUpdate() {
        User u = new User(null,"abc","pass","abc@abc.pl");
        userDao.save(u);
        assert u.getId() != null;
        u.setLogin("bbb");
        u.setPassword("pass2");
        u.setPassword("bbb@bbb.pl");
        userDao.update(u);

        User u2 = userDao.findById(u.getId()).get();
        assert u2.equals(u);
    }

    @Test
    public void testDelete() {
        User u = new User(null,"abc","pass","abc@abc.pl");
        userDao.save(u);
        userDao.delete(u);
        User u2 = userDao.findById(u.getId()).orElse(null);
        assert u2 == null;
    }

    @Test
    public void testSelect() {
        userDao.save( new User(null,"abc","pass","abc@abc.pl"));
        userDao.save( new User(null,"abc2","pass","abc@abc.pl"));
        assert userDao.findAll().size() == 2;
    }
}