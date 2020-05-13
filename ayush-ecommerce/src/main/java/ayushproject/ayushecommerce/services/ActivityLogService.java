package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.ActivityLog;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.repo.ActivityLogRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogService {
    
    @Autowired
    ActivityLogRepository activityLogRepository;
    @Autowired
    UserRepository userRepository;

    public User getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) authentication.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByname(username);
        return user;
    }

    public void activityLog(String message, String tableName, Integer id){
        ActivityLog activityLog=new ActivityLog();
        activityLog.setActivity(message);
        activityLog.setTableName(tableName);
        activityLog.setTableId(id);
        activityLog.setCreatedBy(getLoggedInCustomer().getEmail());
        activityLogRepository.save(activityLog);

    }
}
