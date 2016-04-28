package business.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Transation
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 * 
 */
@Entity
public abstract class Transation {
	@Id
	@GeneratedValue
	private int id;
	@Column
	private double value;
	@Temporal(TemporalType.DATE)
	private Date date;

	public Transation() {
	}

	public Transation(double value, Date d, int idSale) {
		this.date = d;
		this.value = value;
	}

	public double getValor() {
		return value;
	}

	public void setValor(double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
