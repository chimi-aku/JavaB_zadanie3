package wipb.jpademo.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class AccountOperation extends AbstractModel {

    @ManyToOne
    private Account account;

    private BigDecimal amount;

    private OperationType type;


    public AccountOperation() {

    }

    public AccountOperation(Account account, BigDecimal amount, OperationType type) {
        this.account = account;
        this.amount = amount;
        this.type = type;
    }


}
