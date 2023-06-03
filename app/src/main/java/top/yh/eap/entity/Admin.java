package top.yh.eap.entity;

/**
 * @user
 * @date
 */
public class Admin {
    private int id;
    private String phone;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin() {
    }

    public Admin(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }
}
