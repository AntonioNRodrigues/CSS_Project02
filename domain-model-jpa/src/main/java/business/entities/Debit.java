/**
 * 
 */
package business.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import business.Product;

/**
 * @author antonio
 *
 */
@Entity
public class Debit extends Transation {

	
	public Debit() {
		super();
	}

	/**
	 * @param valor
	 * @param d
	 * @param idVenda
	 */
	public Debit(double valor, Date d, int idVenda) {
		super(valor, d, idVenda);
	}
	public List<Product> getAllProdutsByID(){
		return new ArrayList<>();
	}

}
