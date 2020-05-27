package ayushproject.ayushecommerce.dto;

import ayushproject.ayushecommerce.entities.Category;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class CategoryDto  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Category parentCategory;
    private List<Category> immediateChild;

    public CategoryDto(Integer id, String name, Category parentCategory, List<Category> immediateChild) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
        this.immediateChild = immediateChild;
    }

    public CategoryDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getImmediateChild() {
        return immediateChild;
    }

    public void setImmediateChild(List<Category> immediateChild) {
        this.immediateChild = immediateChild;
    }
}
