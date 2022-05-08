package wipb.jpademo.dao;

import wipb.jpademo.model.Account;
import wipb.jpademo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao extends  GenericDao<Account,Long> {


    public List<Account> findAllQuery();
    public List<Account> findByNameAndAddress(String name, String address);
    public List<Account> findNameStartsWith(String name);
    public List<Account> findBetweenBalance(BigDecimal lower, BigDecimal upper);
    public List<Account> findWithMaxBalance();
    public List<Account> findAccountWithoutAnyOperations();
    public List<Account> findAccountWithMostOperations();

}
