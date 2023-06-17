package br.edu.univas.si7.topicos.product.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.univas.si7.topicos.product.entities.CategoryEntity;
import br.edu.univas.si7.topicos.product.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public CategoryEntity save(CategoryEntity category) {
        return repo.saveAndFlush(category);
    }

    public List<CategoryEntity> findAll() {
        return repo.findAll();
    }

    public CategoryEntity update(CategoryEntity category) {
        return repo.saveAndFlush(category);
    }

    public Optional<CategoryEntity> findById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public void delete(CategoryEntity category) {
        repo.delete(category);
    }

}