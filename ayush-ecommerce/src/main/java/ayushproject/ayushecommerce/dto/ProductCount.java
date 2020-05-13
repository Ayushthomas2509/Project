package ayushproject.ayushecommerce.dto;

import ayushproject.ayushecommerce.entities.Category;

public class ProductCount {

    private  Long count;
    private Category categoryId;


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
