package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;

/**
 * Transaction that has a DEBIT type
 */
public class PaymentTransaction extends Transaction {

    /**
     * Type of Transaction
     */
    private final TransactionType type = TransactionType.DEBIT;

    /**
     * Constructor
     * @param persistence Persistence that has access to all TableDataGateways
     */
    public PaymentTransaction(Persistence persistence) {
        super(persistence);
    }

    /**
     * New Transaction operation that invokes the same operation in Transaction, with the right type
     * @param saleId Sale Id of the sale that we want to associate the transaction with
     * @param value The value of the Transaction to be made
     * @return Returns the Id of the new Transaction
     * @throws ApplicationException
     * @throws PersistenceException
     */
    public int newTransaction(int saleId, double value) throws ApplicationException, PersistenceException {
        return super.newTransaction(saleId, value, this.type);
    }
}
