package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.CategoryField;
import ayushproject.ayushecommerce.repo.CategoryFieldRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFieldService {

    @Autowired
    CategoryFieldRepositary categoryFieldRepositary;
    @Autowired
    CategoryFieldValuesService categoryFieldValuesService;

    public Iterable<CategoryField> findAll(){return categoryFieldRepositary.findAll();}

    public String addCategoryField(Integer categoryId,String value,String name){
        if(categoryFieldRepositary.findByName(name)!=null){
            return "Field Exsists";
        }
        else {
            CategoryField categoryField=new CategoryField();
            categoryField.setName(name);
            categoryFieldRepositary.save(categoryField);
            categoryFieldValuesService.beforeCategoryFieldsValues(categoryId,categoryField.getId(),value);
            return "FILE SAVED";

        }
    }
}
