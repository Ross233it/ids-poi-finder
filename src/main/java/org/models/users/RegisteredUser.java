package org.models.users;

public class RegisteredUser implements User{
    private String username;

    private String email;

    private String password;

    private String role;

    public RegisteredUser(String username, String email, String password, String role) {
        this.username = username;
        this.email    = email;
        this.password = password;
        this.role     = role;
    }

    /** getters **/
    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
    public Object[]  getData()  { return new Object[] {
            this.getUsername(),
            this.getEmail(),
            this.getPassword(),
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

    //todo riuovere se non necessari
    /** setters **/
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email)       { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role)         { this.role = role; }


}
