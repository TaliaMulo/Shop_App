package com.example.shopapp.data;

public class User {

    public String age;

    public String email;

    public String name;
    public String phone;


    public User(){}

    public User(String age, String email, String name, String phone) {
        this.age = age;
        this.email = email;
        this.name = name;
        this.phone = phone;


    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
