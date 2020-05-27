package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class Data implements Runnable {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void run() {
        Data data = new Data();
        data.phase1();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data.phase2();

    }

    public synchronized void phase1() {
        RandomUserData randomUserData = new RandomUserData();
        for(Integer i=0; i<10000; i++){
            Customer customer = new Customer();
            customer.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
            customer.setEmail(randomUserData.name()+"@gmail.com");
            customer.setName(randomUserData.name());
            customer.setFirstName(randomUserData.name());
            customer.setLastName(randomUserData.name());
            customer.setPassword(randomUserData.password());
            customer.setAge(randomUserData.number());
            customer.setDob(randomUserData.date());
            customerRepository.save(customer);
        }

    }

    public synchronized void phase2() {
        RandomUserData randomUserData = new RandomUserData();
        for(Integer i=0; i<10000; i++){
            Customer customer = new Customer();
            customer.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
            customer.setEmail(randomUserData.name()+"@gmail.com");
            customer.setName(randomUserData.name());
            customer.setFirstName(randomUserData.name());
            customer.setLastName(randomUserData.name());
            customer.setPassword(randomUserData.password());
            customer.setAge(randomUserData.number());
            customer.setDob(randomUserData.date());
            customerRepository.save(customer);
        }

    }
}
