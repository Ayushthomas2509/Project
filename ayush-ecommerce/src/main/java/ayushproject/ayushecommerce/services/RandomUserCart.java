package ayushproject.ayushecommerce.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomUserCart {

    public String place(){
        Integer leftLimit = 97; // letter 'a'
        Integer rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    public String houseno(){
//        random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                +"/"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public String zipcode(){
//        random from this String
        String NumericString = "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (NumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(NumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public Integer quantity(){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(10);
        return rand_int1;
    }

    public Integer userId(){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(10000);
        return rand_int1;
    }

    public Integer amount(){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(100000);
        return rand_int1;
    }



}
