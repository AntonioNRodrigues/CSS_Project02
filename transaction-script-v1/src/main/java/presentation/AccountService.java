package presentation;

import java.util.ArrayList;
import java.util.List;

import business.AccountTransactionScripts;
import business.ApplicationException;
import dataaccess.AccountRowDataGateway;

public class AccountService {

	private AccountTransactionScripts ATS;
	
	public AccountService(AccountTransactionScripts ats){
		this.ATS = ats;
	}
	
	/**
	 * Get customer accounts
	 * 
	 * @param customerId
	 * @return a textual representation of customer accounts
	 */
	public String getAccountByCustomerId(int vat) throws ApplicationException{	
		
		try{
			// get all customer accounts
			List<AccountRowDataGateway> accounts = ATS.getCustomerAccounts(vat);
			
			// prepare result string
			StringBuilder sb = new StringBuilder();
			sb.append("=============================================\n");
			sb.append("	ID	|	Description	\n");
			for(AccountRowDataGateway account : accounts){
				sb.append("	"+account.getId()+"	|	");
				sb.append("	"+account.getDescription()+"");
				sb.append("\n");
			}
			
			return sb.toString();
			
		}catch(ApplicationException e){
			throw new ApplicationException("Error getting customers accounts", e);
		}
		
		
		
	}
	
	public int addAccount(int vat, String accountDescription) throws ApplicationException{
		try{
			return ATS.insertNewAccount(vat, accountDescription);
		}catch(ApplicationException e){
			throw new ApplicationException("Error inserting new account", e);
		}
	}
	
}
