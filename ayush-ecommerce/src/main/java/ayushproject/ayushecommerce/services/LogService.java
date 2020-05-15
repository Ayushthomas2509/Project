package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Log;
import ayushproject.ayushecommerce.repo.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

    public String info(String message){
        Log log = new Log();
        log.setLogType("INFO");
        log.setLogMessage(message);
        logRepository.save(log);
        return message;
    }

    public String error(String message){
        Log log = new Log();
        log.setLogType("ERROR");
        log.setLogMessage(message);
        logRepository.save(log);
        return message;
    }

    public String warn(String message){
        Log log = new Log();
        log.setLogType("WARN");
        log.setLogMessage(message);
        logRepository.save(log);
        return message;
    }

    public String debug(String message){
        Log log = new Log();
        log.setLogType("DEBUG");
        log.setLogMessage(message);
        logRepository.save(log);
        return message;
    }



}
