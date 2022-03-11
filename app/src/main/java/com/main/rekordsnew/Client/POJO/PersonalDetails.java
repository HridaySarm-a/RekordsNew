package com.main.rekordsnew.Client.POJO;

public class PersonalDetails {
    private String name,fahName,sex,age,dob,educationalQualification,phone,email,cast,password,image;
    private PermanentAddress permanentAddress;
    private BankAccount bankAccount;

    public PersonalDetails(String name, String fahName, String sex, String age, String dob, String educationalQualification, String phone, String email, String cast, String password, String image, PermanentAddress permanentAddress, BankAccount bankAccount) {
        this.name = name;
        this.fahName = fahName;
        this.sex = sex;
        this.age = age;
        this.dob = dob;
        this.educationalQualification = educationalQualification;
        this.phone = phone;
        this.email = email;
        this.cast = cast;
        this.password = password;
        this.image = image;
        this.permanentAddress = permanentAddress;
        this.bankAccount = bankAccount;
    }

    public PersonalDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFahName() {
        return fahName;
    }

    public void setFahName(String fahName) {
        this.fahName = fahName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PermanentAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(PermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "PersonalDetails{" +
                "name='" + name + '\'' +
                ", fahName='" + fahName + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", dob='" + dob + '\'' +
                ", educationalQualification='" + educationalQualification + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", cast='" + cast + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", permanentAddress=" + permanentAddress +
                ", bankAccount=" + bankAccount +
                '}';
    }
}
