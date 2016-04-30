package business.entities;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import business.Sale;

/**
 * Entity implementation class for Entity: Transation
 * 
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 * 
 */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
public abstract class Transation {
	
	@Id @GeneratedValue private int id;
	
	@Column private double value;
	
	@Temporal(TemporalType.DATE) private Date date;
	
	@OneToOne(mappedBy="trans") private Sale sale;
	
	public Transation() {
	}

	public Transation(double value, Date d) {
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	
}
