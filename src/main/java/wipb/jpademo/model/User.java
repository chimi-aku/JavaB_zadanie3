package wipb.jpademo.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="\"USER\"") // konfilkt ze slowem kluczowym SQL w H2 w wersjach 2.x., wymagane escapowanie
public class User extends AbstractModel {

    @Column(unique = true)
    private String login;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserGroup> userGroups = new LinkedList<>();

    public User() {
    }

    public User(Long id, String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void addGroup(String name) {
        // sprawdzic czy już nie jest dodana
        for (UserGroup us : userGroups) {
            if (us.getName().equals(name)) {
                throw new IllegalArgumentException();
            }
        }
        userGroups.add(new UserGroup(name,this));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", login='" + login + '\'' +
                ", password='****'" +
                ", email='" + email + '\'' +
                '}';
    }

}
