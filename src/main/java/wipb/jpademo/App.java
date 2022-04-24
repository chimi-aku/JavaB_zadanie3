package wipb.jpademo;

import wipb.jpademo.dao.AccountDao;
import wipb.jpademo.dao.AccountDaoJpaImpl;
import wipb.jpademo.dao.UserDao;
import wipb.jpademo.dao.UserDaoJpaImpl;
import wipb.jpademo.model.Account;
import wipb.jpademo.model.User;

import java.math.BigDecimal;
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

        AccountDao accountDao = new AccountDaoJpaImpl();

        List<Account> accounts = accountDao.findAll();

        if (!accounts.isEmpty()) {
            System.out.println("accounts:");
            accounts.forEach(System.out::println);

        } else {
            Account account = new Account();
            account.setName("test");
            account.setAddress("address");
            account.setBalance(BigDecimal.valueOf(300));
            accountDao.save(account);
            System.out.println("Saved:"+account);
        }


        BankImpl bank = new BankImpl();

        System.out.println(bank.getBalance(2L));;
        bank.deposit(2L, BigDecimal.valueOf(400));
        System.out.println(bank.getBalance(2L));;
        bank.withdraw(2L, BigDecimal.valueOf(100));
        System.out.println(bank.getBalance(2L));;

        //bank.createAccount("test2", "address2");

        bank.transfer(2L, 1603L, BigDecimal.valueOf(500));


    }
}
