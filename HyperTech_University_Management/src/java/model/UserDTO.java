
package model;

public class UserDTO {
    private String Username;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String created_at;

    public UserDTO(String Username, String name, String email, String password, String phone, String address, String created_at) {
        this.Username = Username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.created_at = created_at;
    }

    public String getUsername() {
        return Username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
}
