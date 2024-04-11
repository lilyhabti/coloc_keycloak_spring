package com.app.coloc.keycloak.dto;

public record UserRegistrationRecord(String username, String email,String firstName,String lastName,String password,String roleName) {
}

