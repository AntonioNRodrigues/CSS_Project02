package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import business.persistence.entities.Transaction;
import facade.exceptions.ApplicationException;

@Remote
public interface IGetCustomerTransactionsRemote {

	public List<Transaction> getCustomerTransactions(int customerVAT) throws ApplicationException;
	
}
