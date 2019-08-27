package com.inrange.trackapplication.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "courier",
        "customer",
        "email",
        "enabled",
        "fullname",
        "id",
        "password",
        "roles",
        "username"
})
public class SignupRequest  implements Serializable {

    @JsonProperty("courier")
    private Boolean courier;
    @JsonProperty("customer")
    private Boolean customer;
    @JsonProperty("email")
    private String email;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("password")
    private String password;
    @JsonProperty("roles")
    private List<Role> roles = new ArrayList<>();
    @JsonProperty("username")
    private String username;

    @JsonProperty("courier")
    public Boolean getCourier() {
        return courier;
    }

    @JsonProperty("courier")
    public void setCourier(Boolean courier) {
        this.courier = courier;
    }

    @JsonProperty("customer")
    public Boolean getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("fullname")
    public String getFullname() {
        return fullname;
    }

    @JsonProperty("fullname")
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("roles")
    public List<Role> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

}