//package ayushproject.ayushecommerce.Thread;
//
//import ayushproject.ayushecommerce.entities.Customer;
//import ayushproject.ayushecommerce.repo.CustomerRepository;
//import ayushproject.ayushecommerce.services.RandomUserData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Optional;
//@Service
//public class CustomerTask implements Runnable {
//    @Autowired
//    RandomUserData randomUserData;
//    @Autowired
//    CustomerRepository customerRepository;
//
//    public CustomerTask() {
//    }
//
//    @Override
//    public void run() {
//        try {
//            for (Integer i = 0; i < 10000; i++) {
//                CustomerTask customerTask = new CustomerTask();
//                customerTask.data(i);
//                Thread.sleep(1000);
//            }
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void data(Integer i){
//        Customer customer = new Customer("Ayush@123",new Date(),"Ayush",
//                "ayushthomas09"+i+"@gmail.com","Thomas",24,"Ayush@123",Arrays.asList("ROLE_CUSTOMER"));
//
//        customerRepository.save(customer);
////        customer.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
////        customer.setEmail("ayushthomas09" + i + "@gmail.com");
////        customer.setName("randomUserData.name()");
////        customer.setFirstName("randomUserData.name()");
////        customer.setLastName("randomUserData.name()");
////        customer.setPassword("Ayush@123");
////        customer.setAge(24);
////        customer.setDob(new Date());
//////                CustomerRepository customerRepository = new CustomerRepository();
//////                }
//    }
//}
