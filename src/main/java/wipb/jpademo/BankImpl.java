package wipb.jpademo;

import wipb.jpademo.dao.AccountDao;
import wipb.jpademo.dao.AccountDaoJpaImpl;
import wipb.jpademo.dao.AccountOperationDao;
import wipb.jpademo.dao.AccountOperationDaoImpl;
import wipb.jpademo.model.Account;

import java.math.BigDecimal;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wipb.jpademo.model.AccountOperation;
import wipb.jpademo.model.OperationType;


public class BankImpl implements Bank{

    private Map<Long, Account> accountList = new HashMap<>();
    private long idNew = 0;

    private final Logger log = LogManager.getLogger(getClass());

    private AccountDao accountDao = new AccountDaoJpaImpl();
    private AccountOperationDao accountOperationDao = new AccountOperationDaoImpl();


    @Override
    public Long createAccount(String name, String address) {
        // Check if threse is existing account
        Long id = findAccount(name, address);
        if(id != null) {
            log.debug("Such account already exists");
            return id;
        }

        // Create new account
        Account account = new Account();
        account.setName(name);
        account.setAddress(address);
        account.setBalance(BigDecimal.ZERO);
        accountDao.save(account);

        log.debug("Account created");

        return account.getId();
    }

    @Override
    public Long findAccount(String name, String address) {
        List<Account> accounts = accountDao.findAll();


        accounts.forEach(account -> {
            if(account.getName().equals(name) && account.getAddress().equals(address)) {
                ;

            }
        });

        log.error("Account not found");
        return null;
    }

    @Override
    public void deposit(Long id, BigDecimal amount) throws AccountIdException {

        Optional<Account> account = accountDao.findById(id);
        Account acc = account.get();

        BigDecimal currBalance = acc.getBalance();
        acc.setBalance(currBalance.add(amount));

        accountDao.update(acc);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAccount(acc);
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.valueOf(OperationType.DEPOSIT.name()));

        accountOperationDao.save(accountOperation);

        log.debug("Deposited succesfully");
    }

    @Override
    public BigDecimal getBalance(Long id) throws AccountIdException {

        Optional<Account> account = accountDao.findById(id);
        Account acc = account.get();

        log.debug("Balance got succesfully");

        return acc.getBalance();

    }

    @Override
    public void withdraw(Long id, BigDecimal amount) throws AccountIdException, InsufficientFundsException {

        Optional<Account> account = accountDao.findById(id);
        Account acc = account.get();

        if(amount.compareTo(BigDecimal.ZERO) > 0 && acc.getBalance().compareTo(amount) >= 0){
            BigDecimal currBalance = acc.getBalance();
            acc.setBalance(currBalance.subtract(amount));

            accountDao.update(acc);

            AccountOperation accountOperation = new AccountOperation();
            accountOperation.setAccount(acc);
            accountOperation.setAmount(amount);
            accountOperation.setType(OperationType.valueOf(OperationType.WITHDRAW.name()));

            accountOperationDao.save(accountOperation);
        }
        else throw new InsufficientFundsException();
    }

    @Override
    public void transfer(Long idSource, Long idDestination, BigDecimal amount) throws AccountIdException, InsufficientFundsException {

        Optional<Account> srcAccount = accountDao.findById(idSource);
        Account srcAcc = srcAccount.get();

        Optional<Account> destAccount = accountDao.findById(idDestination);
        Account destAcc = destAccount.get();

        BigDecimal srcBalance = srcAcc.getBalance();
        BigDecimal destBalance = destAcc.getBalance();

        if(srcBalance.compareTo(amount) <= 0) {
            throw new InsufficientFundsException("Sender has insfficient funds");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException();
        }


        srcAcc.setBalance(srcBalance.subtract(amount));
        accountDao.update(srcAcc);

        AccountOperation accountOperationSend = new AccountOperation();
        accountOperationSend.setAccount(srcAcc);
        accountOperationSend.setAmount(amount);
        accountOperationSend.setType(OperationType.valueOf(OperationType.TRANSFER.name()));

        accountOperationDao.save(accountOperationSend);


        destAcc.setBalance(destBalance.add(amount));
        accountDao.update(destAcc);

        AccountOperation accountOperationReceive = new AccountOperation();
        accountOperationReceive.setAccount(srcAcc);
        accountOperationReceive.setAmount(amount);
        accountOperationReceive.setType(OperationType.valueOf(OperationType.TRANSFER.name()));

        accountOperationDao.save(accountOperationReceive);

        log.debug("Transfered succesfully");
    }

}
