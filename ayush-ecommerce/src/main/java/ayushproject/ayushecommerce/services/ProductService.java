package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.dto.ProductDTO;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.entities.ParentCategory.Electronics;
import ayushproject.ayushecommerce.entities.ParentCategory.Fashion;
import ayushproject.ayushecommerce.exceptions.InvalidFieldException;
import ayushproject.ayushecommerce.exceptions.ProductNotFoundException;
import ayushproject.ayushecommerce.repo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ProductService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    EmailService emailService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryFieldRepo categoryFieldRepo;
    @Autowired
    CategoryFeildValueRepo categoryFeildValueRepo;
    @Autowired
    CategoryRepo categoryRepo;

    public Iterable<Product> allProducts() {
        return productRepo.findAll();
    }


    public List<Product> perCategory(String category) {
        return productRepo.perCategory(category);
    }

    public String addProduct(Product product) {
        productRepo.save(product);
        User admin = userRepo.findById(1).get();
        if (product.getQuantity() <= 0) {

            throw new InvalidFieldException("Quantity Should Be Entered");
        }
        sendMail(admin, "Product Activation", product);
        return "Product Added..";
    }

    public Product findProduct(Integer productId) {
        Product product = productRepo.findById(productId).get();
        if (productId == null) {
            throw new ProductNotFoundException("Invalid Product" + productId);
        } else {
            return product;
        }
    }

    public String editProduct(Product product) {
        productRepo.save(product);
        return "Product Updated";
    }

    private void sendMail(User user, String subject, Product product) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setFrom("Admin_Ayush");
        mailMessage.setText("To complete the " + subject + " process, please click here: "
                + "http://localhost:8080/Enable-product/" + product.getId() + "\n"
                + "Product Details \n"
                + "Product Name " + product.getName()
                + "Product Description " + product.getDescription());

        emailService.sendEmail(mailMessage);
    }

    public String removeProduct(Integer productId) {
        Product product = (productRepo.findById(productId).get());
        product.setDeleted(true);
        productRepo.save(product);
        return "Product deleted";
    }

    public String enableProduct(Integer productId) {
        Product product = findProduct(productId);
        product.setActive(true);
        return "Product Activated";
    }

    public String editElectronicsProduct(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
        Product product = productRepo.findById(id).get();
//        Map Key is the field name , v is value
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Electronics.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, product, v);
        });
        productRepo.save(product);
        return "PRODUCT UPDATED";

    }

    public String editFashionProduct(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
        Product product = productRepo.findById(id).get();
//        Map Key is the field name , v is value
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Fashion.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, product, v);
        });
        productRepo.save(product);
        return "PRODUCT UPDATED";

    }

    public String disableProduct(Integer productId) {
        Product product = findProduct(productId);
        product.setActive(false);
        return "Product De-Activated";
    }

    public String addProductVariation(Integer productId, Integer variationProductId) {
        Product product = findProduct(productId);
        List<Integer> variationList = product.getOtherVariationsId();
        variationList.add(variationProductId);
        product.setOtherVariationsId(variationList);
        productRepo.save(product);
        return "Product Updated";
    }

    public List<Product> findAllProductVariations(Integer productId) {
        Product product = findProduct(productId);
        List<Product> variations = new ArrayList<>();
        List<Integer> variationList = product.getOtherVariationsId();
        Iterator<Integer> variationIterator = variationList.iterator();
        while (variationIterator.hasNext()) {
            variations.add(productRepo.findById(variationIterator.next()).get());
        }
        return variations;
    }

    public List<Product> findSimilarProduct(Integer productId) {
        List<Product> similar = new ArrayList<>();
        Product product = findProduct(productId);
        Iterator<Product> productIterator = productRepo.findAll().iterator();
        while (productIterator.hasNext()) {
            Product currentProduct = productIterator.next();
            if (currentProduct.getCategoryId() == product.getCategoryId()) {

                similar.add(currentProduct);
            }

        }
        return similar;
    }

    public ProductDTO findProductDTO(Integer productId) {
        Product product = productRepo.findById(productId).get();
        if (product == null) {
            throw new ProductNotFoundException("Invalid product Id " + productId);
        } else {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setMetaData(product.getMetaData());
            return productDTO;
        }



    }
}






