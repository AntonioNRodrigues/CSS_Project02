package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

import java.util.Date;

public class Transaction extends TableModule {

    public Transaction(Persistence persistence) {
        super(persistence);
    }

    public int newTransaction(int saleId, double value, Date date, String description, TransactionType type) throws PersistenceException, ApplicationException {
        try {
            return persistence.transactionTableGateway.newTransaction(saleId, value, (java.sql.Date) date, description, type);
        } catch (PersistenceException e) {
            throw new ApplicationException("There was an internal creating a new Transaction", e);
        }
    }
}
