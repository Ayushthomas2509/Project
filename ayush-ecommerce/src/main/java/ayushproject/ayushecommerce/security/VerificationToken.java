package ayushproject.ayushecommerce.security;

import ayushproject.ayushecommerce.entities.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    private String verificationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "userid")
    private User user;

    public VerificationToken(User user) {
        this.user=user;
        createDate=new Date();
        verificationToken = UUID.randomUUID().toString();

    }


    public VerificationToken() { }

//    public static int getEXPIRATION() {
//        return EXPIRATION;
//    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }


    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
