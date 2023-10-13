package com.example.myapp;

public class UserProfile {
    public String Age;
    public String Email;
    public String Name;
    public String MemberName1;
    public String MemberPhone1;
    public String MemberName2;
    public String MemberPhone2;

    public UserProfile() {

    }

    public UserProfile(String age, String email, String name) {
        Age = age;
        Email = email;
        Name = name;
    }

    public UserProfile(String memberName1, String memberPhone1, String memberName2, String memberPhone2) {
        MemberName1 = memberName1;
        MemberPhone1 = memberPhone1;
        MemberName2 = memberName2;
        MemberPhone2 = memberPhone2;
    }

}

