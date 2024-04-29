package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Category;
import com.authentifcation.projectpitwo.repository.CategoryRepository;
import com.authentifcation.projectpitwo.serviceInterface.CategoryInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements CategoryInterface {
    CategoryRepository categoryRepository;
    @Override
    public Category AddCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> AllCategory() {

        return categoryRepository.findAll();
    }

    @Override
    public String DeleteCategory(Long categoryId) {
        if (categoryRepository.findById(categoryId)!=null){
            categoryRepository.deleteById(categoryId);
            return "Offer supprimé";}
        return "Offer non touvable";
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}