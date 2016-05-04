package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

import java.util.Date;

public class PaymentTransaction extends Transaction {

    private final TransactionType type = TransactionType.DEBIT;

    public PaymentTransaction(Persistence persistence) {
        super(persistence);
    }

    public int newTransaction(int saleId, double value, Date date, String description) throws ApplicationException, PersistenceException {
        return super.newTransaction(saleId, value, date, description, this.type);
    }
}
