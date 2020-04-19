package ayushproject.ayushecommerce.entities;

import org.springframework.data.relational.core.sql.In;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    private Integer adminid;

//    //public Admin(){
//        this.addRole(new Role(1,"ROLE_ADMIN"));
//    }

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }
}
