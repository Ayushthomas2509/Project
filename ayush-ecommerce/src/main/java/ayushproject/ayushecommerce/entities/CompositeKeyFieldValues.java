package ayushproject.ayushecommerce.entities;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class CompositeKeyFieldValues implements Serializable {
    private static final long serialVersionUID = 1L;


    @OneToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
    @OneToOne
    @JoinColumn(name = "categoryField_id")
    private CategoryField categoryFieldId;

    public CompositeKeyFieldValues() {
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryField getCategoryFieldId() {
        return categoryFieldId;
    }

    public void setCategoryFieldId(CategoryField categoryFieldId) {
        this.categoryFieldId = categoryFieldId;
    }

    public CompositeKeyFieldValues(Category categoryId, CategoryField categoryFieldId) {
        this.categoryId = categoryId;
        this.categoryFieldId = categoryFieldId;
    }
}
