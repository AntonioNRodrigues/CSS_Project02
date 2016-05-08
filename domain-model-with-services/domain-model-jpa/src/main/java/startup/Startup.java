package startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.ApplicationException;
import business.SaleSys;
import client.SimpleClient;
import presentation.AddCustomerService;
import presentation.GetCustomerTransactionsService;
import presentation.ProcessSaleService;
import presentation.RegistTransactionService;

/**
 * Implements the startup use case. It creates the application, 
 * a simple client that interacts with the application, passing
 * it the application handlers. 
 * 
 * @author fmartins
 * @version 1.0 (08/03/2015)
 *
 */
public class Startup {

	public static void main(String[] args) {
		// Creates the entity manager factory that will assist in persisting entities
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("domain-model-jpa");
			
		// Creates the application main object
		SaleSys app = new SaleSys (emf);

		// Creates the application services
		RegistTransactionService ts = new RegistTransactionService(app.getRegistTransactionHandler());
		
		AddCustomerService cs = new AddCustomerService(app.getAddCustomerHandler());
		ProcessSaleService ss = new ProcessSaleService(app.getProcessSaleHandler(), ts);
		GetCustomerTransactionsService gts = new GetCustomerTransactionsService(app.getCusTomerTransactionHandler(), ts);
		
		// Creates the simple interaction client and 
		// passes it the application handlers
		SimpleClient simpleClient = new SimpleClient(cs, ss, gts);
		simpleClient.createASale();
		
		try{
			simpleClient.closeSale();
			
			simpleClient.makePayment();
			
			simpleClient.checkAccount();
			
		} catch (ApplicationException e) {
			System.out.println("Oops.. something went wrong");
		}
		
		emf.close();
	}
}
