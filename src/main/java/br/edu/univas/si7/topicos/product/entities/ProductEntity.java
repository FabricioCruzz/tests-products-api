package br.edu.univas.si7.topicos.product.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
public class ProductEntity {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	private String name;
	private float price;
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name="FK_ID_CAT")
	private CategoryEntity category;
	
	public ProductEntity(int code, String name, float price, boolean active, CategoryEntity category) {
		super();
		this.code = code;
		this.name = name;
		this.price = price;
		this.active = active;
		this.createdAt = LocalDateTime.now();
		this.category = category;
	}

	@Setter(AccessLevel.NONE)
	private LocalDateTime createdAt;
}
