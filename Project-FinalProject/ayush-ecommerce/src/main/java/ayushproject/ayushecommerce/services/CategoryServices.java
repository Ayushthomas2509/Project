package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.dto.CategoryDTO;
import ayushproject.ayushecommerce.dto.CategoryFilterDTO;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.CategoryFieldValues;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.repo.CategoryFeildValueRepo;
import ayushproject.ayushecommerce.repo.CategoryFieldRepo;
import ayushproject.ayushecommerce.repo.CategoryRepo;
import ayushproject.ayushecommerce.repo.ProductRepo;
import ayushproject.ayushecommerce.security.PasswordValidatorClass;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Iterable<Category> findAll() {
        Pageable paging = PageRequest.of(0, 10, Sort.by("name").ascending());
        return categoryRepo.findAll(paging);
    }

    public String addCategory(String name, Integer parentCategoryId) {
        if (categoryRepo.findByName(name)!=null) {
            return "Category already exsists";
        }
        Category category = new Category(name, parentCategoryId);
        if (parentCategoryId != null) {
            Iterator<Product> productIterator = productRepo.findAll().iterator();
            while (productIterator.hasNext()) {
                Product product = productIterator.next();
                if (product.getCategoryId() == parentCategoryId) {
                    return "Product Category Is Already in use";
                }
            }
            category.setParentCategory(categoryRepo.findById(parentCategoryId).get());
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
            if (currentCategory.getParentId() == categoryId) {
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
        Iterator<Product> productIterator = productRepo.findAll().iterator();
        while (productIterator.hasNext()) {
            Product currentProduct = productIterator.next();
            if (currentProduct.getCategoryId() == categoryId) {
                brandsList.add(currentProduct.getBrand());
                if (currentProduct.getPrice() <= min)
                    min = currentProduct.getPrice();
                if (currentProduct.getPrice() >= max)
                    max = currentProduct.getPrice();
            }
        }
        categoryFilterDTO.setCategoryFieldValues(categoryFieldValuesList);
        categoryFilterDTO.setMaximumPrice(max);
        categoryFilterDTO.setMinimumPrice(min);
        categoryFilterDTO.setBrandsList(brandsList);
        return categoryFilterDTO;

    }
}
