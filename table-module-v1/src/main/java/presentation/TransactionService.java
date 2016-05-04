package presentation;

import business.Transaction;
import dataaccess.Persistence;

public class TransactionService extends Service {

    /**
     * Constructs a service given its persistence repository
     *
     * @param persistence The persistence repository of the service.
     */
    public TransactionService(Persistence persistence) {
        super(persistence);
    }

}
