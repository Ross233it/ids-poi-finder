package org.models.users;

import org.httpServer.AuthUtilities;
import org.models.Model;

/**
 * Questa classe rappresenta un utente registrato all'interno del sistema
 */
public class RegisteredUser extends Model implements IUser {
    private int id;

    private String username;

    private String email;

    private String password;

    private String salt = null;

    private String role = null;

    public  String accessToken = null;

    public RegisteredUser(String username, String email, String password, String role) {
        this.username = username;
        this.email    = email;
        this.password = password;
        this.role     = role;
    }

    /** getters **/
    public int    getId()       { return id; }
    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
    public String getSalt()     { return salt; }
    public String getToken()    { return this.accessToken; }
    public Object[]getData()    { return new Object[] {
            this.getId(),
            this.getUsername(),
            this.getEmail(),
            this.getPassword(),
            this.getSalt(),
            this.getRoleId()}
    ;}




    /**
     * Verifica se un utente ha un determinato ruolo
     * @param role
     * @return
     */
    public Boolean hasRole(String role) {
        return this.role.equals(role);
    }

    //todo convertire ruoli in id se c'e' tempo
    /**
     * Ritorna un id
     * @return
     */
    public int getRoleId(){
        switch(this.getRole()){
            case "admin":
                return 1;
            case "contributor":
                return 2;
            case "authContributor":
                return 3;
            case "curator":
                return 4;
            default: return 2;
        }
    }

    /** setters **/
    public void setId(int id)                { this.id = id; }
    public void setAccessToken(String token){
        this.accessToken = AuthUtilities.generateAccessToken(this.username);};
    public void setSalt(String salt)         { this.salt = salt; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email)       { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role)         { this.role = role; }
}
