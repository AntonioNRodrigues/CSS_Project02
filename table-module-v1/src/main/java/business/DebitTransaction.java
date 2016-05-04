package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

import java.util.Date;

public class DebitTransaction extends Transaction {

    private final TransactionType type = TransactionType.CREDIT;

    public DebitTransaction(Persistence persistence) {
        super(persistence);
    }

    public int newTransaction(int saleId, double value, String description) throws ApplicationException, PersistenceException {
        return super.newTransaction(saleId, value, description, this.type);
    }
}
