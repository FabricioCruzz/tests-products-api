package br.edu.univas.si7.topicos.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.univas.si7.topicos.product.dto.ProductDTO;
import br.edu.univas.si7.topicos.product.entities.ProductEntity;
import br.edu.univas.si7.topicos.product.repository.ProductRepository;
import br.edu.univas.si7.topicos.product.support.ObjectNotFoundException;
import br.edu.univas.si7.topicos.product.support.ProductException;

public class ProductServiceTest {

//	private static ProductRepository repo;
//	private ProductService service;
//	
//	@BeforeEach
//	public void setup() {
//		repo = Mockito.mock(ProductRepository.class);
//		service = new ProductService(repo);
//		
//		ProductEntity prod01 = new ProductEntity(1, "test", 0f, false);
//		Mockito.when(repo.findById(1)).thenReturn(Optional.of(prod01));
//		
//		List<ProductEntity> listProd = new ArrayList<>();
//		listProd.add(prod01);
//		Mockito.when(repo.findAll()).thenReturn(listProd);
//	}
//
//	@Test
//	void testGetAllProducts() {
//		List<ProductDTO> allProducts = service.findAll();
//		assertNotNull(allProducts);
//		assertEquals(1, allProducts.size());
//		assertEquals(1, allProducts.get(0).getCode());
//	}
//
//	@Test
//	void testGetProductById() {
//		ProductEntity product = service.findById(1);
//		assertNotNull(product);
//		assertEquals(1, product.getCode());
//	}
//	
//	@Test
//	void testSaveProduct() {
//		ProductEntity prod01 = new ProductEntity(1, "test", 0f, false);
//		Mockito.when(repo.save(Mockito.any(ProductEntity.class))).thenReturn(prod01);
//		
//		service.createProduct(new ProductDTO(prod01));
//		Mockito.verify(repo, Mockito.times(1)).save(Mockito.any());
//	}
//	
//	@Test
//	void testUpdateProduct() {
//		ProductEntity prod01 = new ProductEntity(1, "test", 0f, false);
//		Mockito.when(repo.save(Mockito.any(ProductEntity.class))).thenReturn(prod01);
//		
//		//create a product
//		service.createProduct(new ProductDTO(prod01));
//		prod01.setName("new_name");
//		
//		//update the name of the product
//		service.updateProduct(prod01, 1);
//
//		//verifies that the save(...) method has been called
//		Mockito.verify(repo, Mockito.times(2)).save(Mockito.any());
//		//verify findById
//		
//		//pesquisar sobre o ArgumentCaptor do Mockito para ter certeza que o update foi feito corretamente
//	}
//	
//	//TODO: scenario: find active products with empty list
//	//TODO: scenario: find active products with non empty list
//	//TODO: scenario: update product with invalid data
//	//TODO: scenario: update product with non existing product
//	
//	@Test
//	void testDeleteSuccess() {
//		Mockito.doNothing().when(repo).delete(Mockito.any());
//		service.deleteProduct(1);
//	}
//	
//	@Test
//	void testDeleteWithCodeNull() {
//		assertThrows(ProductException.class, () -> service.deleteProduct(null));
//	}
//	
//	@Test
//	void testDeleteWithExcetion() {
//		Mockito.doThrow(DataIntegrityViolationException.class)
//			.when(repo).delete(Mockito.any());
//		assertThrows(ProductException.class, () -> service.deleteProduct(1));
//	}
//	
//	@Test
//	void testDeleteNonExistingProduct() {
//		assertThrows(ObjectNotFoundException.class, () -> service.deleteProduct(2));
//	}
}
