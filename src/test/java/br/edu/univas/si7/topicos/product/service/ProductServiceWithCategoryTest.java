package br.edu.univas.si7.topicos.product.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.univas.si7.topicos.product.dto.ProductDTO;
import br.edu.univas.si7.topicos.product.dto.ProductDTONew;
import br.edu.univas.si7.topicos.product.entities.CategoryEntity;
import br.edu.univas.si7.topicos.product.entities.ProductEntity;
import br.edu.univas.si7.topicos.product.repository.CategoryRepository;
import br.edu.univas.si7.topicos.product.repository.ProductRepository;
import br.edu.univas.si7.topicos.product.support.ObjectNotFoundException;
import br.edu.univas.si7.topicos.product.support.ProductException;

public class ProductServiceWithCategoryTest {
	
	@Mock
	private ProductRepository repo;
	
	@Mock
	private CategoryRepository repoCat;
	
	@InjectMocks
	private ProductService service;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		CategoryEntity cat01 = new CategoryEntity("name01", "family01", "group01");
		ProductEntity prod01 = new ProductEntity(1, "testProd", 0f, false, cat01);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(prod01));
		
		List<ProductEntity> listProd = new ArrayList<>();
		listProd.add(prod01);
		Mockito.when(repo.findAll()).thenReturn(listProd);
	}
	
	@Test
	void testGetAllProducts() {
		List<ProductDTO> allProducts = service.findAll();
		assertNotNull(allProducts);
		assertEquals(1, allProducts.size());
		assertEquals(1, allProducts.get(0).getCode());
	}
	
	@Test
	void testFindAllActive() {
		List<ProductEntity> products = new ArrayList<>();
		products.add(new ProductEntity(1, "prod01", 10.0f, true, new CategoryEntity("category01", "family01", "group01")));
		products.add(new ProductEntity(2, "prod02", 20.0f, true, new CategoryEntity("category02", "family02", "group02")));
		Mockito.when(repo.findByActive(true)).thenReturn(products);
		
		List<ProductDTO> allProducts = service.findAllActive();
		assertNotNull(allProducts);
		assertEquals(2, allProducts.size());
		assertEquals(1, allProducts.get(0).getCode());
		assertEquals(2, allProducts.get(1).getCode());
	}
	
	@Test
	void testGetProductById_ExistingProduct() {
		ProductEntity product = service.findById(1);
		assertNotNull(product);
		assertEquals(1, product.getCode());
	}
	
	@Test
	public void testFindById_NonExistingProduct() {
		Mockito.when(repo.findById(2)).thenReturn(Optional.empty());
		
		try {
			service.findById(2);
		} catch(ObjectNotFoundException e) {
			assertEquals("Product 2 not found", e.getMessage());
		}
		
		Mockito.verify(repo,Mockito.times(1)).findById(2);
	}
	
	@Test
	void testFindCategoryByName() {
		Mockito.when(repoCat.findByName("name01")).thenReturn(true);
		Boolean existingCategory = service.findByCatName("name01");
		assertTrue(existingCategory);
	}
	
	@Test
	void testFindProdByCatName() {
		List<ProductDTO> allProducts = service.findProdByCatName("name01");
		assertNotNull(allProducts);
		assertEquals(1, allProducts.size());
		assertEquals(1, allProducts.get(0).getCode());
	}
	
	@Test
	void testSaveProduct_ExistingCategory() {
		CategoryEntity cat01 = new CategoryEntity("name01", "family01", "group01");		
		ProductEntity prod01 = new ProductEntity(2, "testProd02", 0f, false, cat01);
		Mockito.when(repoCat.findByName("name01")).thenReturn(true);

		service.createProduct(new ProductDTONew(prod01));
		
		Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(ProductEntity.class));
		Mockito.verify(repoCat, Mockito.never()).save(Mockito.any(CategoryEntity.class));
		
	}
	
	@Test
	void testSaveProduct_CreatingNewCategory() {
		CategoryEntity cat01 = new CategoryEntity("test100", "test", "test");
		ProductEntity prod01 = new ProductEntity(3, "testProd02", 0f, false, cat01);
		Mockito.when(repoCat.findByName("test100")).thenReturn(false);
		
		
		service.createProduct(new ProductDTONew(prod01));
		Mockito.when(repo.save(Mockito.any(ProductEntity.class))).thenReturn(prod01);
		
		
		Mockito.verify(repoCat, Mockito.times(1)).save(Mockito.any(CategoryEntity.class));
		Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(ProductEntity.class));
	}
	
	@Test
	void testUpdateProduct_Success() {
		CategoryEntity cat01 = new CategoryEntity("name01", "family01", "group01");
		ProductEntity prod01 = new ProductEntity(1, "test", 0f, false, cat01);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(prod01));
		
		service.updateProduct(prod01, 1);
		
		Mockito.verify(repo, Mockito.times(1)).save(prod01);
	}
	
	@Test
	void testUpdateProduct_CodeIsNull() {
		CategoryEntity cat01 = new CategoryEntity("name01", "family01", "group01");
		ProductEntity prod01 = new ProductEntity(1, "test", 0f, false, cat01);
		
		try {
			service.updateProduct(prod01, null);
		} catch(ProductException e) {
			assertEquals("Invalid product code.", e.getMessage());
		}
	}
	
	@Test
	void testUpdateProduct_ProductIsNull() {
		try {
			service.updateProduct(null, 1);
		} catch(ProductException e) {
			assertEquals("Invalid product code.", e.getMessage());
		}
	}
	
	@Test
	void testUpdateProduct_CodeNotMatch() {
		CategoryEntity cat01 = new CategoryEntity("name01", "family01", "group01");
		ProductEntity prod01 = new ProductEntity(1, "test", 0f, false, cat01);
		
		try {
			service.updateProduct(prod01, 2);
		} catch (ProductException e) {
			assertEquals("Invalid product code.", e.getMessage());			
		}
	}
	
	@Test
	void testDelete_Success() {
		Mockito.doNothing().when(repo).delete(Mockito.any());
		service.deleteProduct(1);
		ProductEntity obj = service.findById(1);
		Mockito.verify(repo, Mockito.times(1)).delete(obj);
	}
	
	@Test
	void testDelete_CodeIsNull() {
		try {
			service.deleteProduct(null);
		} catch (ProductException e) {
			assertEquals("Product code can not be null.", e.getMessage());
		}
	}
	
	@Test
	void testDelete_Exception() {
		ProductEntity prod = new ProductEntity();
		prod.setCode(1);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(prod));
		
		Mockito.doThrow(DataIntegrityViolationException.class)
			.when(repo).delete(prod);
		
		try {
			service.deleteProduct(1);
			fail("Expected ProductException to be thrown");
		} catch (ProductException e) {
			assertEquals("Can not delete a Product with dependencies constraints.", e.getMessage());			
		}
	}
	
	@Test
	void testDelete_NonExistingProduct() {
		try {
			service.deleteProduct(4);
		} catch (ObjectNotFoundException e) {
			assertEquals("Product 4 not found", e.getMessage());
		}
	}
}
