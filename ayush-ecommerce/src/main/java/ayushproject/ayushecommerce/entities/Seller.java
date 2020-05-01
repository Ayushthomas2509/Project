package ayushproject.ayushecommerce.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("Seller")
public class Seller extends User {
   //     private Integer sellerId;
        private Integer GST_No;
        @Embedded
        private Address address;
        @OneToMany(cascade = CascadeType.ALL)
        private List<Product> products;

//    public Integer getSellerId() {
//            return sellerId;
//        }
//
//        public void setSellerId(Integer sellerId) {
//            this.sellerId = sellerId;
//        }

//    public Seller(String firstName) {
//        this.firstName= firstName;
//    }
//
//        public Seller(List<String> authoritiesList) {
//            setAuthoritiesList(Arrays.asList("ROLE_SELLER"));
//            this.authoritiesList= authoritiesList;
//        }

        public Integer getGST_No() {
            return GST_No;
        }

        public void setGST_No(Integer GST_No) {
            this.GST_No = GST_No;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }




    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}




