package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.dto.CategoryDto;
import ayushproject.ayushecommerce.dto.CategoryFilterDto;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.CategoryFieldValues;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.exceptions.ProductNotFoundException;
import ayushproject.ayushecommerce.repo.CategoryFeildValueRepository;
import ayushproject.ayushecommerce.repo.CategoryFieldRepositary;
import ayushproject.ayushecommerce.repo.CategoryRepository;
import ayushproject.ayushecommerce.repo.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryFieldRepositary categoryFieldRepositary;
    @Autowired
    CategoryFeildValueRepository categoryFeildValueRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    //private java.lang.Object Object;

    Logger logger = LoggerFactory.getLogger("CategoryService.class");

    @Cacheable(cacheNames = "categories")
    public Iterable<Category> findAll() {
        logger.info("List of category accessed");
        Pageable paging = PageRequest.of(0, 10, Sort.by("name").ascending());
        return categoryRepository.findAll(paging);
    }

    public String addCategory(Category category) {
        if (categoryRepository.findByName(category.getName())!=null) {
            throw new ProductNotFoundException("Category already exists");
        }
        categoryRepository.save(category);
        return "Category Saved";
    }

    public CategoryDto findCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        CategoryDto categoryDTO = modelMapper.map(category, CategoryDto.class);
        List<Category> categoryList = new ArrayList<>();
        Iterator<Category> categoryIterator = categoryRepository.findAll().iterator();
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
        if (categoryRepository.findByName(newName)!=null) {
            return "Category Exsists";
        }
        Category category = categoryRepository.findById(categoryId).get();
        category.setName(newName);
        categoryRepository.save(category);
        return "Category Updated";
    }

    public Iterable<CategoryDto> leafnodeCategories() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        Iterator<Category> categoryIterator = categoryRepository.findAll().iterator();
        while (categoryIterator.hasNext()) {
            CategoryDto currentCategory = findCategory(categoryIterator.next().getId());
            if (currentCategory.getImmediateChild().isEmpty()) {
                categoryDtoList.add(currentCategory);
            }
        }
        return categoryDtoList;
    }

    public CategoryFilterDto CategoryFilter(Integer categoryId) {
        CategoryFilterDto categoryFilterDTO = new CategoryFilterDto();
        List<CategoryFieldValues> categoryFieldValuesList = new ArrayList<>();
        Iterator<CategoryFieldValues> categoryFieldValuesIterator = categoryFeildValueRepository.findAll().iterator();
        while (categoryFieldValuesIterator.hasNext()) {
            CategoryFieldValues currentCategoryFieldValues = categoryFieldValuesIterator.next();
            if (currentCategoryFieldValues.getId().getCategoryId().getId() == categoryId) {
                categoryFieldValuesList.add(currentCategoryFieldValues);
            }
        }

        Integer max = Integer.MIN_VALUE;
        Integer min = Integer.MAX_VALUE;
        Set<String> brandsList = new HashSet<>();
        Optional<Category> category= categoryRepository.findById(categoryId);
//        System.out.println("\n\n\n\n"+ category.get().getName());
//        Optional<Category> parentCategory = categoryRepository.findByParentCategory(category.get().getParentCategory());
//        System.out.println("\n\n\n\n"+ parentCategory.get().getName());
        List<Product> product = productRepository.findByCategory(category.get());
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
