package spittr;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shawnritchie on 28/04/15.
 */
public class Spitter implements Serializable{

    private static final long serialVersionUID = 4482204544217776481L;

    private Spitter() {
    }

    private Long id;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private boolean updateByEmail;

    private String status;

    private List<Spittle> spittles;

    public Spitter(Long id, String username, String password, String fullName,
                   String email, boolean updateByEmail) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.updateByEmail = updateByEmail;
        this.status = "Newbie";
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isUpdateByEmail() {
        return updateByEmail;
    }

    public String getStatus() {
        return status;
    }

    public List<Spittle> getSpittles() {
        return spittles;
    }
}