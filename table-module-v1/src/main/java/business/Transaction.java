package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;

/**
 * Class tha represents a transaction that relates to a Customer and a Sale
 * Is a TableModule so it has a corresponding Table in the DB
 */
public class Transaction extends TableModule {

    /**
     * Constructor
     * @param persistence Persistence that has all the access to the Gateways
     */
    public Transaction(Persistence persistence) {
        super(persistence);
    }

    /**
     * New Transaction operation that inserts a new Transaction in the DB
     * @param saleId Sale Id that we want associate
     * @param value Value of the new Transaction
     * @param type Type of the Transaction
     * @return Id of the new Transaction created
     * @throws PersistenceException
     * @throws ApplicationException
     */
    public int newTransaction(int saleId, double value, TransactionType type) throws PersistenceException, ApplicationException {
        try {
            return persistence.transactionTableGateway.newTransaction(saleId, value, type);
        } catch (PersistenceException e) {
            throw new ApplicationException("There was an internal error creating a new Transaction", e);
        }
    }

    /**
     * Gets all Transactions from the sale with saleId as Id
     * @param saleId Sale Id of the Sale that has the Transactions
     * @return TableData that has Rows that represent the Transactions of the Sale with Id saleId
     * @throws ApplicationException
     */
    public TableData getAllTransactionsFromSale(int saleId) throws ApplicationException {
        try {
            TableData td = persistence.transactionTableGateway.getAllTransactions(saleId);
            return td;
        } catch (PersistenceException e) {
            throw new ApplicationException("There was an internal error getting all Transaction Ids", e);
        }
    }

    /**
     * Textual representation of a Transaction
     * @param row Row that has the data of the Transaciton
     * @return String that represents a Transaction
     * @throws PersistenceException
     */
    public String print(TableData.Row row) throws PersistenceException {
        return this.persistence.transactionTableGateway.readId(row) + " | "
                + this.persistence.transactionTableGateway.readType(row) + " | "
                + this.persistence.transactionTableGateway.readCreatedAt(row) + " | "
                + this.persistence.transactionTableGateway.readValue(row);
    }
}
