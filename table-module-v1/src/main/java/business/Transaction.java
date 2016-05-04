package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

import java.util.Date;

public class Transaction extends TableModule {

    public Transaction(Persistence persistence) {
        super(persistence);
    }

    public int newTransaction(int saleId, double value, String description, TransactionType type) throws PersistenceException, ApplicationException {
        try {
            return persistence.transactionTableGateway.newTransaction(saleId, value, description, type);
        } catch (PersistenceException e) {
            throw new ApplicationException("There was an internal error creating a new Transaction", e);
        }
    }
}
