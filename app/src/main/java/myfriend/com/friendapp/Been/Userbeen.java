package myfriend.com.friendapp.Been;

import org.litepal.crud.LitePalSupport;

public class Userbeen extends LitePalSupport {
    public Userbeen() {
    }

    public Userbeen(String address, String username, String userid, String phone, String gender, String pwd, String age) {
        Address = address;
        Username = username;
        Userid = userid;
        Phone = phone;
        Gender = gender;
        Pwd = pwd;
        Age = age;
    }

    /**
     * Address : 1
     * Username : 1
     * Userid : 1
     * Phone : 1
     * Gender : 1
     * Pwd : 1
     * Age : 1
     */

    private String Address;
    private String Username;
    private String Userid;
    private String Phone;
    private String Gender;
    private String Pwd;
    private String Age;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String Userid) {
        this.Userid = Userid;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String Pwd) {
        this.Pwd = Pwd;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }


}
