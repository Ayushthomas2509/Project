package ayushproject.ayushecommerce.dto;

import org.json.simple.JSONObject;

public class ProductDTO {
    private Integer id;
    private String name;
    private String brand;
    private Integer CategoryId;
    private String desciption;
    private Integer quantity;
    private Integer price;
    private Integer sellerId;
    private JSONObject metadataDTO;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
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

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public JSONObject getMetadataDTO() {
        return metadataDTO;
    }

    public void setMetadataDTO(JSONObject metadataDTO) {
        this.metadataDTO = metadataDTO;
    }
}
