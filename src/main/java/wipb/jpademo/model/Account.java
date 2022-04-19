package wipb.jpademo.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "\"ACCOUNT\"")
public class Account extends AbstractModel {

    public String name;

    @Column(unique = true)
    public String address;

    public BigDecimal balance;

    @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountOperation> accountOperations = new LinkedList<>();


    public Account(){

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

}
