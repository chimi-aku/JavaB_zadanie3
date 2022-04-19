package wipb.jpademo;

import wipb.jpademo.dao.UserDao;
import wipb.jpademo.dao.UserDaoJpaImpl;
import wipb.jpademo.model.User;

import java.util.List;


public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        UserDao dao = new UserDaoJpaImpl();

        List<User> users = dao.findAll();
        if (!users.isEmpty()) {
            System.out.println("users:");
            users.forEach(System.out::println);
        } else {
            User user = new User();
            user.setLogin("abc");
            user.setPassword("secret");
            user.setEmail("abc@abc.pl");
            dao.save(user);
            System.out.println("Saved:"+user);
        }

    }
}
