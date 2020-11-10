package mtf.project.service;

import mtf.project.model.UserRoleModel;

import java.util.List;

public interface UserService {
    UserRoleModel getUserById(String id);

    UserRoleModel getUserByUsername(String username);

    public String encrypt(String password);

    List<UserRoleModel> getAllUser();

    UserRoleModel addUser(UserRoleModel user);

    List<UserRoleModel> getUserByRoleNama(String string);
}