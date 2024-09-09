package com.userService.dto;

public class GetUserResponse {
    private String name;
    private String email;
    private String role;

    public GetUserResponse() {
    }

    public GetUserResponse(String role, String name, String email) {
        this.role = role;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
