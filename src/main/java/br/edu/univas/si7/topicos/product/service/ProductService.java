package br.edu.univas.si7.topicos.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.edu.univas.si7.topicos.product.dto.ProductDTO;
import br.edu.univas.si7.topicos.product.dto.ProductDTONew;
import br.edu.univas.si7.topicos.product.entities.CategoryEntity;
import br.edu.univas.si7.topicos.product.entities.ProductEntity;
import br.edu.univas.si7.topicos.product.repository.CategoryRepository;
import br.edu.univas.si7.topicos.product.repository.ProductRepository;
import br.edu.univas.si7.topicos.product.support.ObjectNotFoundException;
import br.edu.univas.si7.topicos.product.support.ProductException;

@Service
public class ProductService {

	private ProductRepository repo;
	private CategoryRepository repoCat;

	@Autowired
	public ProductService(ProductRepository repo, CategoryRepository repoCat) {
		this.repo = repo;
		this.repoCat = repoCat;
	}
	
	public List<ProductDTO> findAll() {
		return repo.findAll().stream().map(p -> new ProductDTO(p)).collect(Collectors.toList());
	}

	public List<ProductDTO> findAllActive() {
		return repo.findByActive(true).stream().map(p -> new ProductDTO(p)).collect(Collectors.toList());
	}

	public ProductEntity findById(Integer code) {
		Optional<ProductEntity> obj = repo.findById(code);
		ProductEntity entity = obj.orElseThrow(() -> new ObjectNotFoundException("Product " + code + " not found"));
		return entity;
	}
	
	public Boolean findByCatName(String nameCat) {
		return repoCat.findByName(nameCat);
	}
	
	public List<ProductDTO> findProdByCatName(String name) {
		return repo.findAll().stream()
				.filter(p -> p.getCategory().getName().equals(name))
				.map(p -> new ProductDTO(p)).collect(Collectors.toList()); 
	}

	public void createProduct(ProductDTONew product) {
		System.out.println(repoCat);
		if(findByCatName(product.getNameCat())) {//true
			repo.save(toEntity(product));
		}
		else {//false
			CategoryEntity category = new CategoryEntity(product.getNameCat(), product.getFamily(), product.getGroup()); 
			repoCat.save(category);
			repo.save(toEntity(product));
		}
	}
	
	public ProductEntity toEntity(ProductDTONew prodNew) {
		CategoryEntity cat = new CategoryEntity(prodNew.getNameCat(), prodNew.getFamily(), prodNew.getGroup());
		return new ProductEntity(prodNew.getCode(), prodNew.getName(), prodNew.getPrice(), true, cat);
	}

	public void updateProduct(ProductEntity product, Integer code) {
		if (code == null || product == null || !code.equals(product.getCode())) {
			throw new ProductException("Invalid product code.");
		}
		ProductEntity existingObj = findById(code);
		updateData(existingObj, product);
		repo.save(existingObj);
	}

	private void updateData(ProductEntity existingObj, ProductEntity obj) {
		existingObj.setName(obj.getName());
	}

	public void deleteProduct(Integer code) {
		if (code == null) {
			throw new ProductException("Product code can not be null.");
		}
		ProductEntity obj = findById(code);
		try {
			repo.delete(obj);
			// desativar o produto (ao invés de deletar)
		} catch (DataIntegrityViolationException e) {
			throw new ProductException("Can not delete a Product with dependencies constraints.");
		}
	}

}
