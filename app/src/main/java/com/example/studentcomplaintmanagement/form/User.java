package com.example.studentcomplaintmanagement.form;

public class User
{
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String gender;
    private String branch;
    private int year;
    private String section;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static User toUser(String s) {

        User user=new User();

        String[] tokens=s.split(":");

        user.setUsername(tokens[0]);
        user.setEmail(tokens[1]);
        user.setPassword(tokens[2]);
        user.setMobile(tokens[3]);
        user.setGender(tokens[4]);
        user.setBranch(tokens[5]);
        user.setYear(Integer.parseInt(tokens[6]));
        user.setSection(tokens[7]);
        user.setType(tokens[8]);
        return user;
    }

    @Override
    public String toString() {
        return username+":"+email+":"+password+":"+mobile+":"+gender+":"+branch+":"+year+":"+section+":"+type;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


}
