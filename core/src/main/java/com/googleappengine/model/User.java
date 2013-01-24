package com.googleappengine.model;

import com.google.appengine.api.datastore.Key;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"JpaAttributeTypeInspection"})
@Entity
public class User extends BaseEntity implements Serializable, UserDetails {
    @Override
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key getId() {
        return super.getId();
    }

    @Override
    public void setId(Key id) {
        super.setId(id);
    }
    @Basic
    private String firstName;
    @Basic
    private String lastName;
    @Basic
    private String middleName;
    @Basic
    private String username;
    @Basic
    private String password;
    @Basic
    private String email;
    @Basic
    private String phoneNumber;
    @Basic
    private Date birthDate;
    @Basic
    private String gender;
    @Basic
    private String personalId;
    @Basic
    private String personalIdType;
    @Basic
    private String issuePlace;
    @Basic
    private Date issueDate;
    @Basic
    private float customerValue;

    @Basic
    private List<String> roleNames;

    // Spring required properties
    @Basic
    private boolean enabled;
    @Basic
    private List<Long> wordList = new ArrayList<Long>();
    @Transient
    private boolean accountExpired;
    @Transient
    private boolean accountLocked;
    @Transient
    private boolean credentialExpired;

    public static final String[] SKIP_FIELDS_USER =
            {"jdoDetachedState", "password", "roleNames", "wordList", "enabled", "accountExpired",
                    "accountLocked", "credentialExpired"};

    public enum PersonalIdType {
        CIVIL,
        VISA
    }

    public enum CustomerGender {
        MALE,
        FEMALE
    }

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);

        for (String s : roleNames) {
            authorities.add(new SimpleGrantedAuthority(s));
        }

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Transient
    public boolean isAccountNonExpired() {
        return !accountLocked;
    }

    @Transient
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public void setWordList(List<Long> wordList) {
        this.wordList = wordList;
    }

    public List<Long> getWordList() {
        return wordList;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialExpired() {
        return credentialExpired;
    }

    public void setCredentialExpired(boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public float getCustomerValue() {
        return customerValue;
    }

    public void setCustomerValue(float customerValue) {
        this.customerValue = customerValue;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonalIdType() {
        return personalIdType;
    }

    public void setPersonalIdType(String personalIdType) {
        this.personalIdType = personalIdType;
    }
}
