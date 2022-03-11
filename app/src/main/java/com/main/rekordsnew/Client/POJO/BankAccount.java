package com.main.rekordsnew.Client.POJO;

public class BankAccount {
    private String accountNo,bankName,branch,ifscCode;

    public BankAccount(String accountNo, String bankName, String branch, String ifscCode) {
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.branch = branch;
        this.ifscCode = ifscCode;
    }

    public BankAccount() {
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
