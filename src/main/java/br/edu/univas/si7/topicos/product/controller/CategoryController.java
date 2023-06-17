package br.edu.univas.si7.topicos.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.univas.si7.topicos.product.dto.CategoryDto;
import br.edu.univas.si7.topicos.product.entities.CategoryEntity;
import br.edu.univas.si7.topicos.product.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("")
    public ResponseEntity<CategoryEntity> saveEquip(@Valid @RequestBody CategoryDto dto) {
        var equip = new CategoryEntity();
        BeanUtils.copyProperties(dto, equip);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(equip));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryEntity>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Integer id) {
        Optional<CategoryEntity> optionalEquipment = service.findById(id);
        if (!optionalEquipment.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category not Found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalEquipment.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Integer id,
                                         @RequestBody @Valid CategoryDto dto) {
        Optional<CategoryEntity> optionalEquip = service.findById(id);
        if (!optionalEquip.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        var equip = new CategoryEntity();
        BeanUtils.copyProperties(dto, equip);
        equip.setId(optionalEquip.get().getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.save(equip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEquip(@PathVariable(value = "id") Integer id) {
        Optional<CategoryEntity> newEquip = service.findById(id);
        if (!newEquip.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        service.delete(newEquip.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category deleted successfully");
    }

}