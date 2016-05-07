package presentation;

import business.MakePaymentHandler;
import business.Sale;

public class MakePaymentService {

	private MakePaymentHandler makePaymentHandler;
	
	public MakePaymentService(MakePaymentHandler makePaymentHandler){
		this.makePaymentHandler = makePaymentHandler;
	}
	
	public void makePayment(Sale sale, double value){
		makePaymentHandler.makePayment(sale, value);
	}
	
}
