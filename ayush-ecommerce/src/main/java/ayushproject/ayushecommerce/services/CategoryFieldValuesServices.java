package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.CategoryFieldValues;
import ayushproject.ayushecommerce.entities.CompositeKeyFieldValues;
import ayushproject.ayushecommerce.repo.CategoryFeildValueRepo;
import ayushproject.ayushecommerce.repo.CategoryFieldRepo;
import ayushproject.ayushecommerce.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryFieldValuesServices<CategoryFieldValuesRepo> {
    @Autowired
    CategoryFieldRepo categoryFieldRepo;

    @Autowired
    CategoryFeildValueRepo categoryFieldValuesRepo;

    @Autowired
    CategoryRepo categoryRepo;

    public Iterable<CategoryFieldValues> findAll(){
        return categoryFieldValuesRepo.findAll();
    }


    public String beforeCategoryFieldsValues(Integer categoryId, Integer categoryFieldId, String value) {
        CategoryFieldValues categoryFieldValues=new CategoryFieldValues(new CompositeKeyFieldValues(categoryRepo.findById(categoryId).get(),categoryFieldRepo.findById(categoryFieldId).get()), Arrays.asList(value));
        categoryFieldValuesRepo.save(categoryFieldValues);
        return "Field value added";
    }

    public String addCategoryFieldsValues(Integer categoryId,Integer categoryFieldId, String newValue) {
        CategoryFieldValues categoryFieldValues=categoryFieldValuesRepo.findById(new CompositeKeyFieldValues(categoryRepo.findById(categoryId).get(),categoryFieldRepo.findById(categoryFieldId).get())).get();
        List<String> values=categoryFieldValues.getPossibleValues();
        if(values.contains(newValue)){
            return "Value already Exists";
        }
        values.add(newValue);
        categoryFieldValues.setPossibleValues(values);
        categoryFieldValuesRepo.save(categoryFieldValues);
        return "Value added";
    }

}
