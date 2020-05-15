package ayushproject.ayushecommerce.dto;

import java.io.Serializable;
import java.util.Map;

public class VariationDto implements Serializable {

    private Integer id;
    private Integer quantity;
    private Integer price;
    private String image;
    private Map<String,String> metaData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public VariationDto(Integer id,Integer quantity, Integer price, String image, Map<String, String> metaData) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.metaData = metaData;
    }
}
