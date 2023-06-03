package top.yh.eap.entity;

import androidx.annotation.NonNull;

/**
 * @user
 * @date
 */
public class Employee {
    private int id;
    private String username;
    private String phone;

    private String password;
    private boolean remember;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }

    public Employee(String username, String phone, String password, boolean remember) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.remember = remember;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
