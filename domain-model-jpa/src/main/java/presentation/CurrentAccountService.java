package presentation;

import business.ApplicationException;
import business.CurrentAccountHandler;
import business.Customer;
import business.entities.Account;

public class CurrentAccountService {
	
	private CurrentAccountHandler accountHandler;

	public CurrentAccountService(CurrentAccountHandler current) {
		this.accountHandler = current;
	}
	public Customer getCustomer(int vat) throws ApplicationException{
		return accountHandler.getCustomer(vat);
	}
	public Account getAccount(int id_account)throws ApplicationException{
		return accountHandler.getAccount(id_account);
	}

}
