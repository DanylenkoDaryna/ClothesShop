package ua.nure.danylenko.epam.db.entity;

import java.time.LocalDate;

/**
 * The User class provides fields and methods that describes user of site
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class User extends Entity {

    private static final long serialVersionUID = -6889036256149495388L;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String country;

    private LocalDate birthday;

    private String email;

    private String telephone;

    private int roleId;

    private AccountStatus accountStatus;

    public User(){
        accountStatus=AccountStatus.UNLOCKED;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User [login=" + login
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", roleId=" + roleId + "]";
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * Method for converting string of Size we have from db to the exact enum value
     * @param value AccountStatus with String type
     */
    public void extractAccountStatus(String value){
        AccountStatus as = AccountStatus.valueOf(value.toUpperCase());
        this.setAccountStatus(as);
    }
}
