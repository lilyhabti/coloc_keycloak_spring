package com.app.coloc.keycloak.service;

import com.app.coloc.keycloak.dto.UserRegistrationRecord;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class KeycloakUserServiceImpl implements KeycloakUserService{

    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;

    public KeycloakUserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord) {

//        System.out.println(userRegistrationRecord.firstName());
//        System.out.println(userRegistrationRecord.lastName());

        UserRepresentation user=new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationRecord.username());
        user.setEmail(userRegistrationRecord.email());
        user.setFirstName(userRegistrationRecord.firstName());
        user.setLastName(userRegistrationRecord.lastName());
        user.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRecord.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        UsersResource usersResource = getUsersResource();
//        System.out.println(user.getFirstName()+"  " + user.getLastName() + "   " +user.getUsername());

        Response response = usersResource.create(user);

        System.out.println("Status" + response.getStatus());
//        if(response.getStatus() >= 200 && response.getStatus() < 300){
//            System.out.println(response.getStatus());
////            String userId = response.readEntity(UserRepresentation.class).getId();
//            Object object =  response.readEntity(Object.class);
//            System.out.println(object + " obj");
//            System.out.println("NO ERROR");
//            String roleName = userRegistrationRecord.roleName();
////            assignRole(userId, roleName);
//
//            List<UserRepresentation> representationList = usersResource.searchByUsername(userRegistrationRecord.username(), true);
//            if(!CollectionUtils.isEmpty(representationList)){
//                UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
//                assert userRepresentation1 != null;
////                emailVerification(userRepresentation1.getId());
//            }
//            return  userRegistrationRecord;
//        }

//        response.readEntity()

        return null;
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {


        return  getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {

        getUsersResource().delete(userId);
    }


//    @Override
//    public void emailVerification(String userId){
//
//        UsersResource usersResource = getUsersResource();
//        usersResource.get(userId).sendVerifyEmail();
//    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void assignRole(String userId, String roleName) {

        UserResource userResource = getUserResource(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));

    }

    private RolesResource getRolesResource(){
        return  keycloak.realm(realm).roles();
    }

//    @Override
//    public void updatePassword(String userId) {
//
//        UserResource userResource = getUserResource(userId);
//        List<String> actions= new ArrayList<>();
//        actions.add("UPDATE_PASSWORD");
//        userResource.executeActionsEmail(actions);
//
//    }
}
