package com.guilhermefgl.shopcart.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "cart")
public class Cart implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id
	@Column(name = "id_cart")
	@SequenceGenerator(name = "product_seq", sequenceName = "product_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
	private Long id;

	@Column(nullable = false)
	String user;

	@Column(nullable = false)
	private Boolean closed = false;

	private LocalDateTime dateTime;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cart_has_product", joinColumns = @JoinColumn(name = "id_cart"), 
				inverseJoinColumns = @JoinColumn(name = "id_product"))
	private List<Product> products;
	
	private Double total = 0d;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public LocalDateTime getDate() {
		return dateTime;
	}

	public void setDate(LocalDateTime date) {
		this.dateTime = date;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
