package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.CategoryField;
import ayushproject.ayushecommerce.repo.CategoryFieldRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFieldServices {

    @Autowired
    CategoryFieldRepo categoryFieldRepo;
    @Autowired
    CategoryFieldValuesServices categoryFieldValuesServices;

    public Iterable<CategoryField> findAll(){return categoryFieldRepo.findAll();}

    public String addCategoryField(Integer categoryId,String value,String name){
        if(categoryFieldRepo.findByName(name)!=null){
            return "Field Exsists";
        }
        else {
            CategoryField categoryField=new CategoryField();
            categoryFieldRepo.save(categoryField);
            categoryFieldValuesServices.beforeCategoryFieldsValues(categoryId,categoryField.getId(),value);
            return "FILE SAVED";

        }
    }
}
