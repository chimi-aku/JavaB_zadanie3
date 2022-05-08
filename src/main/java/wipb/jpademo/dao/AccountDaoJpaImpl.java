package wipb.jpademo.dao;

import wipb.jpademo.model.Account;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoJpaImpl extends GenericDaoJpaImpl<Account,Long> implements  AccountDao{

    EntityManager em = getEntityManager();


    @Override
    public List findAllQuery() {
        return em.createNamedQuery("Account.findAll").getResultList();
    }

    @Override
    public List findByNameAndAddress(String name, String address) {
        return em.createNamedQuery("Account.findByNameAndAddress").
                setParameter(1,name).
                setParameter(2,address)
                .getResultList();
    }

    @Override
    public List findNameStartsWith(String name) {
        return em.createNamedQuery("Account.findNameStartsWith")
                .setParameter(1,name+"%")
                .getResultList();
    }

    @Override
    public List findBetweenBalance(BigDecimal lower, BigDecimal upper) {
        return em.createNamedQuery("Account.findBetweenBalance")
                .setParameter(1,lower)
                .setParameter(2,upper)
                .getResultList();
    }

    @Override
    public List findWithMaxBalance() {
        return em.createNamedQuery("Account.findWithMaxBalance")
                .getResultList();
    }

    @Override
    public List findAccountWithoutAnyOperations() {
        return em.createNamedQuery("Account.findAccountWithoutAnyOperations")
                .getResultList();
    }

    @Override
    public List findAccountWithMostOperations() {

        List res = em.createNamedQuery("Account.findAccountWithMostOperations")
                .getResultList();

        List <Account> accountList = new ArrayList<Account>();

        for (Object account : res) {

            Long id = ((Account) account).getId();
            String name = ((Account) account).getName();
            String address = ((Account) account).getAddress();
            BigDecimal balance = ((Account) account).getBalance();

            Account a = new Account(id, name, address, balance);
            accountList.add(a);
        }

        return res;
    }
}
