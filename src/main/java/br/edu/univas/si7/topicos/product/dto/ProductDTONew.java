package br.edu.univas.si7.topicos.product.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.edu.univas.si7.topicos.product.entities.ProductEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTONew {

	@NotNull(message = "Code can not be null.")
	@Positive(message = "Code must not be negative.")
	private Integer code;

	@NotNull(message = "Name can not be null.")
	@NotEmpty(message = "Name can not be empty.")
	@Size(min = 5, max = 80, message = "The size must be between 5 and 80.")
	private String name;

	@NotNull(message = "Price can not be null.")
	@Positive(message = "Price must not be negative.")
	private float price;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dateCreated;

//  @NotNull(message = "Active can not be null.")
//	private boolean active;
	
	private String nameCat;
	private String family;
	private String group;

	public ProductDTONew(ProductEntity product) {
		this.code = product.getCode();
		this.name = product.getName();
		this.price = product.getPrice();
		this.dateCreated = product.getCreatedAt();
//		this.active = product.isActive();
		this.nameCat = product.getCategory().getName();
		this.family = product.getCategory().getFamily();
		this.group = product.getCategory().getGroup();
	}
}
