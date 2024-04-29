package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Category;
import com.authentifcation.projectpitwo.serviceImplimentation.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CategoryController")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    CategoryService categoryService;
@PostMapping("/AddCategory")
    public Category AddCategory(@RequestBody Category category) {
        return categoryService.AddCategory(category);
    }
@GetMapping("/AllCategory")
    public List<Category> AllCategory() {
        return categoryService.AllCategory();
    }
@DeleteMapping("/DeleteCategory/{categoryId}")
    public String DeleteCategory(@PathVariable("categoryId") Long categoryId) {
        return categoryService.DeleteCategory(categoryId);
    }
@PutMapping("/modifierCategory")
    public ResponseEntity<?> modifierCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return ResponseEntity.ok("Categorie modifiée avec succès");
    }
    @GetMapping("/getCategoryById/{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }
}
