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

//        System.out.println(bank.getBalance(2L));;
//        bank.deposit(2L, BigDecimal.valueOf(400));
//        System.out.println(bank.getBalance(2L));;
//        bank.withdraw(2L, BigDecimal.valueOf(100));
//        System.out.println(bank.getBalance(2L));;
//
        //bank.createAccount("XD", "123");
//
//        bank.transfer(2L, 1603L, BigDecimal.valueOf(500));
//

        System.out.println("\n******* TASK 4 *******\n");

        AccountDaoJpaImpl accountDaoJpa = new AccountDaoJpaImpl();

        System.out.println("\nfindAll");
        System.out.println(accountDaoJpa.findAll());
        System.out.println("\nfindByNameAndAddress");
        System.out.println(accountDaoJpa.findByNameAndAddress("test2", "address2"));
        System.out.println("\nfindNameStartsWith");
        System.out.println(accountDaoJpa.findNameStartsWith("te"));
        System.out.println("\nfindBetweenBalance");
        System.out.println(accountDaoJpa.findBetweenBalance(BigDecimal.valueOf(200), BigDecimal.valueOf(800)));
        System.out.println("\nfindWithMaxBalance");
        System.out.println(accountDaoJpa.findWithMaxBalance());
        System.out.println("\nfindAccountWithoutAnyOperations");
        System.out.println(accountDaoJpa.findAccountWithoutAnyOperations());
        System.out.println("\nfindAccountWithMostOperations");
        System.out.println(accountDaoJpa.findAccountWithMostOperations());
    }
}
