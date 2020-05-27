package ayushproject.ayushecommerce.services;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

@Service
public class RandomUserData {

    public String name() {
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

    public Integer number() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(60);
        return rand_int1;
    }

    public String password() {

//        random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                +"@$*-%"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {

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

    public Date date(){
        int startYear=1994;									//Starting year of specified random date
        int endYear=2015;									//Starting year of specified random date (including)
        long start = Timestamp.valueOf(startYear+1+"-1-1 0:0:0").getTime();
        long end = Timestamp.valueOf(endYear+"-1-1 0:0:0").getTime();
        long ms=(long) ((end-start)*Math.random()+start);	//The qualified number of 13-bit milliseconds is obtained.
        return new Date(ms);
    }
}