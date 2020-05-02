package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.dto.CategoryDTO;
import ayushproject.ayushecommerce.dto.CategoryFilterDTO;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.CategoryFieldValues;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.exceptions.ProductNotFoundException;
import ayushproject.ayushecommerce.repo.CategoryFeildValueRepo;
import ayushproject.ayushecommerce.repo.CategoryFieldRepo;
import ayushproject.ayushecommerce.repo.CategoryRepo;
import ayushproject.ayushecommerce.repo.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryServices {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    CategoryFieldRepo categoryFieldRepo;
    @Autowired
    CategoryFeildValueRepo categoryFeildValueRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    //private java.lang.Object Object;

    public Iterable<Category> findAll() {
        Pageable paging = PageRequest.of(0, 10, Sort.by("name").ascending());
        return categoryRepo.findAll(paging);
    }

    public String addCategory(Category category) {
        if (categoryRepo.findByName(category.getName())!=null) {
            throw new ProductNotFoundException("Category already exists");
        }
        categoryRepo.save(category);
        return "Category Saved";
    }

    public CategoryDTO findCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).get();
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        List<Category> categoryList = new ArrayList<>();
        Iterator<Category> categoryIterator = categoryRepo.findAll().iterator();
        while (categoryIterator.hasNext()) {
            Category currentCategory = categoryIterator.next();
//            System.out.println(categoryId+">>>>>>>>>>);
            if (currentCategory.getParentCategory() == category) {
                categoryList.add(currentCategory);
            }
        }
        categoryDTO.setImmediateChild(categoryList);
        return categoryDTO;
    }

    public String editCategory(String newName, Integer categoryId) {
        if (categoryRepo.findByName(newName)!=null) {
            return "Category Exsists";
        }
        Category category = categoryRepo.findById(categoryId).get();
        category.setName(newName);
        categoryRepo.save(category);
        return "Category Updated";
    }

    public Iterable<CategoryDTO> leafnodeCategories() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        Iterator<Category> categoryIterator = categoryRepo.findAll().iterator();
        while (categoryIterator.hasNext()) {
            CategoryDTO currentCategory = findCategory(categoryIterator.next().getId());
            if (currentCategory.getImmediateChild().isEmpty()) {
                categoryDTOList.add(currentCategory);
            }
        }
        return categoryDTOList;
    }

    public CategoryFilterDTO CategoryFilter(Integer categoryId) {
        CategoryFilterDTO categoryFilterDTO = new CategoryFilterDTO();
        List<CategoryFieldValues> categoryFieldValuesList = new ArrayList<>();
        Iterator<CategoryFieldValues> categoryFieldValuesIterator = categoryFeildValueRepo.findAll().iterator();
        while (categoryFieldValuesIterator.hasNext()) {
            CategoryFieldValues currentCategoryFieldValues = categoryFieldValuesIterator.next();
            if (currentCategoryFieldValues.getId().getCategoryId().getId() == categoryId) {
                categoryFieldValuesList.add(currentCategoryFieldValues);
            }
        }

        Integer max = Integer.MIN_VALUE;
        Integer min = Integer.MAX_VALUE;
        Set<String> brandsList = new HashSet<>();
        Optional<Category> category=categoryRepo.findById(categoryId);
//        System.out.println("\n\n\n\n"+ category.get().getName());
//        Optional<Category> parentCategory = categoryRepo.findByParentCategory(category.get().getParentCategory());
//        System.out.println("\n\n\n\n"+ parentCategory.get().getName());
        List<Product> product = productRepo.findByCategory(category.get());
        for (Product objects: product){
            System.out.println(objects.getBrand());
                brandsList.add(objects.getBrand());
                if (objects.getPrice() <= min)
                    min = objects.getPrice();
                if (objects.getPrice() >= max)
                    max =  objects.getPrice();
            }
        categoryFilterDTO.setCategoryFieldValues(categoryFieldValuesList);
        if (max>0)
            categoryFilterDTO.setMaximumPrice(max);
        if(min<Integer.MAX_VALUE)
            categoryFilterDTO.setMinimumPrice(min);


        categoryFilterDTO.setBrandsList(brandsList);
        return categoryFilterDTO;

    }
}
