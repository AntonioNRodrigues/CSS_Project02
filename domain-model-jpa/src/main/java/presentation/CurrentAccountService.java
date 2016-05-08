package presentation;

import java.util.List;

import business.ApplicationException;
import business.CurrentAccountHandler;
import business.entities.Transation;

public class CurrentAccountService {

	private CurrentAccountHandler currentAccountHandler;

	public CurrentAccountService(CurrentAccountHandler current) {
		this.currentAccountHandler = current;
	}
	
	public boolean validateCustomer(int vat) throws ApplicationException{
		return currentAccountHandler.validateCustomer(vat);
	}

	public List<Transation> getAllTransations(int vat) throws ApplicationException {
		return currentAccountHandler.getAllTransations(vat);
	}

	public String seeTransation(Transation t) throws ApplicationException{
		return currentAccountHandler.seeTransation(t);
		
	}

	
	

}
