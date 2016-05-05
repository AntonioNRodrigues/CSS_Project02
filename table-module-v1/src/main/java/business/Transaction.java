package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;

import java.util.*;

public class Transaction extends TableModule {

    public Transaction(Persistence persistence) {
        super(persistence);
    }

    public int newTransaction(int saleId, double value, TransactionType type) throws PersistenceException, ApplicationException {
        try {
            return persistence.transactionTableGateway.newTransaction(saleId, value, type);
        } catch (PersistenceException e) {
            throw new ApplicationException("There was an internal error creating a new Transaction", e);
        }
    }

    public List<Integer> getAllTransactionsIds(int saleId) throws ApplicationException {
        try {
            TableData td = persistence.transactionTableGateway.getAllTransactions(saleId);
            Iterator<TableData.Row> iter = td.iterator();
            List<Integer> list = new ArrayList<Integer>();
            while (iter.hasNext()) {
                TableData.Row row = iter.next();
                list.add(persistence.transactionTableGateway.readId(row));
            }
        } catch (PersistenceException e) {
            throw new ApplicationException("There was an internal error creating a new Transaction", e);
        }
    }
}
