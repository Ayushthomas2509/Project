package ayushproject.ayushecommerce.dto;

import ayushproject.ayushecommerce.entities.CategoryFieldValues;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Set;

public class CategoryFilterDto {
    private List<CategoryFieldValues> categoryFieldValues;
    private Set<String> brandsList;
    private Integer maximumPrice=0;
    private Integer minimumPrice=0;

    public List<CategoryFieldValues> getCategoryFieldValues() {
        return categoryFieldValues;
    }

    public void setCategoryFieldValues(List<CategoryFieldValues> categoryFieldValues) {
        this.categoryFieldValues = categoryFieldValues;
    }

    public Set<String> getBrandsList() {
        return brandsList;
    }

    public void setBrandsList(Set<String> brandsList) {
        this.brandsList = brandsList;
    }

    public Integer getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(Integer maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public Integer getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Integer minimumPrice) {
        this.minimumPrice = minimumPrice;
    }
}
