package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.dto.ProductDto;
import ayushproject.ayushecommerce.dto.VariationDto;
import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.User;
//import ayushproject.ayushecommerce.entities.ParentCategory.Electronics;
//import ayushproject.ayushecommerce.entities.ParentCategory.Fashion;
import ayushproject.ayushecommerce.enums.InStock;
import ayushproject.ayushecommerce.exceptions.InvalidFieldException;
import ayushproject.ayushecommerce.exceptions.ProductNotFoundException;
import ayushproject.ayushecommerce.repo.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component

public class ProductService  implements Serializable{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryFieldRepositary categoryFieldRepositary;
    @Autowired
    CategoryFeildValueRepository categoryFeildValueRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ActivityLogService activityLogService;

    Logger logger = LoggerFactory.getLogger("ProductService.class");

    public User getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) authentication.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByname(username);
        return user;
    }


    @Cacheable(cacheNames = "products")
    public Iterable<Product> allProducts() {
        logger.info("list of products received");
        //activityLogService.activityLog("View all products","product",null);
        return productRepository.findAll();
    }


    public List<Product> perCategory(Integer category) {
        activityLogService.activityLog("View All Products by Category","product",null);
        Optional<Category> category1= categoryRepository.findById(category);
        return productRepository.perCategory(category1.get());
    }

    public String addProduct(Product product) {
        productRepository.save(product);
        User admin = userRepository.findById(1).get();
        if (product.getQuantity() <= 0) {

            throw new InvalidFieldException("Quantity Should Be Entered");
        }
        sendMail(admin, "Product Activation", product);
        activityLogService.activityLog("Product Added","product",null);
        return "Product Added..";
    }

    public Product findProduct(Integer productId) {
        Product product = productRepository.findById(productId).get();
        if (productId == null) {
            throw new ProductNotFoundException("Invalid Product" + productId);
        } else {
            activityLogService.activityLog("Single Product Is Displayed","product",null);
            return product;
        }
    }

    public String editProduct(Product product) {
        activityLogService.activityLog("Product Is Edited","product",null);
        productRepository.save(product);
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
        Product product = (productRepository.findById(productId).get());
        activityLogService.activityLog("Product Is Removed","product",null);
        product.setDeleted(true);
        productRepository.save(product);
        return "Product deleted";
    }

    public String enableProduct(Integer productId) {
        Product product = findProduct(productId);
        activityLogService.activityLog("Product is Enabled","product",null);
        product.setActive(true);
        return "Product Activated";
    }

    public String editElectronicsProduct(Integer id, Product product) {
        User user = getLoggedInCustomer();
        Optional<Product> productOptional = productRepository.findById(id);
        if(product.getQuantity()!=null)
            productOptional.get().setQuantity(product.getQuantity());
        if(product.getCategory()!=null)
            productOptional.get().setCategory(product.getCategory());
        if(product.getDescription()!=null)
            productOptional.get().setDescription(product.getDescription());
        if(product.getName()!=null)
            productOptional.get().setName(product.getName());
        if(product.getBrand()!=null)
            productOptional.get().setBrand(product.getBrand());
        if(product.getPrice()!=null)
            productOptional.get().setPrice(product.getPrice());
        productOptional.get().setSellerId(user.getId());
        productRepository.save(productOptional.get());
        activityLogService.activityLog("Product is Updated","product",null);
        return "PRODUCT UPDATED";

    }

//    public String editFashionProduct(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
//        Product product = productRepository.findById(id).get();
////        Map Key is the field name , v is value
//        fields.forEach((k, v) -> {
//            Field field = ReflectionUtils.findField(Fashion.class, k);
//            field.setAccessible(true);
//            ReflectionUtils.setField(field, product, v);
//        });
//        productRepository.save(product);
//        return "PRODUCT UPDATED";

    public String disableProduct(Integer productId) {
    Product product = findProduct(productId);
        product.setActive(false);
        activityLogService.activityLog("Product is disabled","product",null);
        return "Product De-Activated";
    }

    public String addProductVariation(Integer productId, VariationDto variationDto) {
        activityLogService.activityLog("Product Variation Is Added","product",null);
        Product product = findProduct(productId);
        Product product1= new Product();
        List<Product> productList = (List<Product>) productRepository.findAll();
        for(Product product2: productList)
            product1.setId(product2.getId()+1);
        product1.setName(product.getName());
        product1.setBrand(product.getBrand());
        product1.setDescription(product.getDescription());
        product1.setSellerId(product.getSellerId());
        product1.setCategory(product.getCategory());
        product1.setInStock(InStock.Yes);
        product1.setPrice(variationDto.getPrice());
        product1.setQuantity(variationDto.getQuantity());
        product1.setMetaData(variationDto.getMetaData());
        product1.setProductImage(variationDto.getImage());
        productRepository.save(product1);
        return "Product Updated";
    }

    public List<VariationDto> findAllProductVariations(Integer productId) {
        activityLogService.activityLog("All Product Variations are Displayed","product",null);
        Product product = findProduct(productId);
        List<Product> variations = productRepository.findByName(product.getName());
//        System.out.println(variations.get(0).getName());
        List<VariationDto> variationDtos= new ArrayList<>();
        for (Product variation: variations){
            VariationDto variationDto=new VariationDto(productId,variation.getQuantity(),variation.getPrice(),variation.getProductImage(),variation.getMetaData());
            variationDtos.add(variationDto);
        }
        return variationDtos;
    }

    public List<Product> findSimilarProduct(Integer productId) {
        activityLogService.activityLog("Similar Products Are displayed","product",null);
        Optional<Product> productOptional= productRepository.findById(productId);
        List<Product> similarProduct = productRepository.findByCategory(productOptional.get().getCategory());
        return similarProduct;
        }

    public ProductDto findProductDTO(Integer productId) {
        Product product = productRepository.findById(productId).get();
        if (product == null) {
            throw new ProductNotFoundException("Invalid product Id " + productId);
        } else {
            ProductDto productDTO = modelMapper.map(product, ProductDto.class);
            productDTO.setMetaData(product.getMetaData());
            return productDTO;
        }



    }
}






