package client;

import presentation.AddCustomerService;
import presentation.CurrentAccountService;
import presentation.ProcessSaleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.INTERNAL;

import business.entities.Transation;

import business.ApplicationException;
import business.CurrentAccountHandler;
import business.Customer;
import business.DiscountCatalog;
import business.entities.Account;
import business.entities.AccountCatalog;

/**
 * A simple application client that uses both application handlers.
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 */
public class SimpleClient {

	private AddCustomerService addCustomerService;
	private ProcessSaleService processSaleService;
	private CurrentAccountService currentAccountService;

	public SimpleClient(AddCustomerService addCustomerService, ProcessSaleService processSaleService,
			CurrentAccountService currentAccountService) {
		this.addCustomerService = addCustomerService;
		this.processSaleService = processSaleService;
		this.currentAccountService = currentAccountService;
	}

	/**
	 * A simple interaction with the application services
	 */
	public void createASale() {
		System.out.println("ADD_CUSTOMER_SERVICE: " + addCustomerService);

		System.out.println("PROCES_SSALE_SERVICE: " + processSaleService);

		// the interaction
		try {
			// adds a customer.

			//addCustomerService.addCustomer(168027852, "Customer 1", 217500255, 1, new Account());
			// addCustomerService.addCustomer(224531700, "Antonio", 217500255, 2, new Account());
			// starts a new sale
			
			processSaleService.newSale(168027852);
			// adds two products to the sale
			processSaleService.addProductToSale(123, 3);
			processSaleService.addProductToSale(124, 1);
			//processSaleService.addProductToSale(123, 1);

			System.out.println(processSaleService.getSaleDiscount());

			int idSale = processSaleService.closeSale(168027852);
			if (idSale != -1) {
				System.out.println("The Sale has been closed");
				System.out.println("Sarting its payment");
				System.out.println("ID_SALE " + idSale);
				paySale(idSale, 168027852);

			} else {
				System.out.println("Something went Wrong");
			}

		} catch (ApplicationException e) {
			System.out.println("Error: " + e.getMessage());
			// for debugging purposes only. Typically, in the application
			// this information can be associated with a "details" button when
			// the error message is displayed.
			if (e.getCause() != null)
				System.out.println("Cause: ");
			e.printStackTrace();
		}
	}

	public void checkAccount() throws ApplicationException {
		System.out.println("Running the CheckAccount use case");

		System.out.println("Insert vat number o client");

		int vat = 168027852;

		try {
			currentAccountService.validateCustomer(vat);
		} catch (Exception e) {
			throw new ApplicationException("Client has not been found", e);
		}

		currentAccountService.getAllTransations(vat).isEmpty();
		List<Transation> lista = currentAccountService.getAllTransations(vat);
		
		Map<Integer, Transation> mapa = new HashMap<>(lista.size());

		int i = 0;
		for (Transation t : lista) {
			mapa.put(i, t);
			System.out.println("Numero: " + i + " Conteudo: " + t);
			i++;
		}
		System.out.println("Selecione o numero da Transaction que quer consultar");
		int numberSelected = 15;

		String str = currentAccountService.seeTransation(mapa.get(numberSelected));
		System.out.println(str);
	}

	public void paySale(int idSale, int vat) throws ApplicationException {
		processSaleService.paySale(idSale, vat);

	}
}
