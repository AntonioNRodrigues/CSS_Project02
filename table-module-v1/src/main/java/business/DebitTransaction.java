package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

/**
 * Transaction that has a type of Credit
 */
public class DebitTransaction extends Transaction {

    /**
     * Type of Transaction
     */
    private final TransactionType type = TransactionType.CREDIT;

    /**
     * Constructor
     * @param persistence Persistence that has access to all TableDataGateways
     */
    public DebitTransaction(Persistence persistence) {
        super(persistence);
    }

    /**
     * New Transaction operation that invokes the same operation in Transaction class
     * But it invokes it with the correct type
     * @param saleId Id of the sale we want to associate the Transaction with
     * @param value Value of the Transaction
     * @return Id of the new Transaction that was created
     * @throws ApplicationException
     * @throws PersistenceException
     */
    public int newTransaction(int saleId, double value) throws ApplicationException, PersistenceException {
        return super.newTransaction(saleId, value, this.type);
    }
}
