public class User {
    private String id;
    private String phone;
    private String password;
    private String userName;
    public User(){};
    public User(String userName,String id,String phone,String password){
        this.userName = userName;
        this.id = id;
        this.phone = phone;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

}
