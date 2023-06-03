package top.yh.eap.entity;

/**
 * @user
 * @date
 */
public class SignIn {
    private int id;
    private String username;
    private String phone;
    private String time;



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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SignIn() {
    }

    public SignIn(String username, String phone, String time) {
        this.username = username;
        this.phone = phone;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
