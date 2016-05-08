package business;

import java.util.List;

import business.entities.Account;
import business.entities.AccountCatalog;
import business.entities.Credit;
import business.entities.Debit;
import business.entities.Transation;
import business.entities.TransationCatalog;

public class CurrentAccountHandler {

	private CustomerCatalog customerCatalog;

	private AccountCatalog accountCatalog;

	public CurrentAccountHandler(SaleCatalog saleCatalog, CustomerCatalog customerCatalog,
			TransationCatalog transationCatalog, AccountCatalog accountCatalog) {
		this.customerCatalog = customerCatalog;
		this.accountCatalog = accountCatalog;

	}

	/**
	 * method to get all transations of a customer
	 * 
	 * @param vat
	 * @return
	 * @throws ApplicationException
	 */
	public List<Transation> getAllTransations(int vat) throws ApplicationException {

		Customer c = customerCatalog.getCustomer(vat);
		Account a = accountCatalog.getAccount(c.getAccount().getId());
		return a.getTransations();
	}

	public boolean validateCustomer(int vat) throws ApplicationException {
		return customerCatalog.getCustomer(vat) != null ? false : true;

	}

	/**
	 * method to see the content of the transation depending of its type
	 * 
	 * @param t
	 * @return info of Sale if its the instance os Debit and the date and its id
	 *         if its of type Credit
	 * @throws ApplicationException
	 */
	public String seeTransation(Transation t) throws ApplicationException {

		List<SaleProduct> lista = null;

		StringBuilder sb = new StringBuilder();

		if (t instanceof Debit) {
			lista = ((Debit) t).getInfoSale();

		} else if (t instanceof Credit) {

			return sb.append(t.getDate().toString()).append(" ").append(t.getSale().getIdSale()).toString();

		} else {

			throw new ApplicationException("Problem geting the Transation");
		}
		return lista.toString();
	}
}