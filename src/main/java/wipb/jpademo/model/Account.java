package wipb.jpademo.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "\"ACCOUNT\"")
@NamedQueries({
        @NamedQuery(
                name = "Account.findAll",
                query = "SELECT e FROM Account e"
        ),
        @NamedQuery(
                name = "Account.findByNameAndAddress",
                query = "SELECT e FROM Account e WHERE e.name LIKE ?1 AND e.address LIKE ?2"
        ),
        @NamedQuery(
                name = "Account.findNameStartsWith",
                query = "SELECT e FROM Account e WHERE e.name LIKE ?1"
        ),
        @NamedQuery(
                name = "Account.findBetweenBalance",
                query = "SELECT e FROM Account e WHERE e.balance BETWEEN ?1 AND ?2"
        ),
        @NamedQuery(
                name = "Account.findWithMaxBalance",
                query = "SELECT e FROM Account e WHERE e.balance = (SELECT MAX(f.balance) FROM Account f)"
        ),
        @NamedQuery(
                name = "Account.findAccountWithoutAnyOperations",
                query = "SELECT e FROM Account e WHERE e.id NOT IN (SELECT DISTINCT f.account.id FROM AccountOperation f)"
        )

})
@NamedNativeQueries(
        @NamedNativeQuery(
                name = "Account.findAccountWithMostOperations",
                query = "SELECT * FROM ACCOUNT a WHERE a.id IN ( SELECT g.account_id FROM AccountOperation g GROUP BY g.account_id HAVING COUNT(*) = (SELECT MAX(e.count) FROM (SELECT COUNT(*) AS count FROM AccountOperation f GROUP BY f.account_id) e))",
                resultClass = Account.class
        )
)
public class Account extends AbstractModel {

    public String name;

    @Column(unique = true)
    public String address;

    public BigDecimal balance;

    @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountOperation> accountOperations = new LinkedList<>();


    public Account(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AccountOperation> getAccountOperations() {
        return accountOperations;
    }

    public void setAccountOperations(List<AccountOperation> accountOperations) {
        this.accountOperations = accountOperations;
    }

    public Account(Long id, String name, String address, BigDecimal balance) {
        this.name = name;
        this.address = address;
        this.balance = balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String toString() {
        return "Account{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", address='" + this.getAddress() + '\'' +
                ", balance='" + this.getBalance() + '\'' +
                '}';
    }
}
