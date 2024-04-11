package com.app.coloc.keycloak.service;

import com.app.coloc.keycloak.dto.UserRegistrationRecord;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

    UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord);
    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
//    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void assignRole(String userId,String roleName);

//    void updatePassword(String userId);
}

