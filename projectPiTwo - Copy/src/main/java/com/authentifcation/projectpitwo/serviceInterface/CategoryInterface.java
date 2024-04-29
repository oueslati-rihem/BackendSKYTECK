package com.authentifcation.projectpitwo.serviceInterface;


import com.authentifcation.projectpitwo.entities.Category;

import java.util.List;

public interface CategoryInterface {

    Category AddCategory(Category category);
    List<Category> AllCategory();
    String DeleteCategory(Long categoryId);
    Category updateCategory(Category category);
    Category getCategoryById(Long categoryId);
}
