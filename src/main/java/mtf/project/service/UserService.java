package mtf.project.service;

import mtf.project.model.UserRoleModel;

import java.util.List;

public interface UserService {
    UserRoleModel getUserById(String id);

    UserRoleModel getUserByUsername(String username);

    String encrypt(String password);

    List<UserRoleModel> getAllUser();

    UserRoleModel updateUser(UserRoleModel user);

    UserRoleModel addUser(UserRoleModel user);

    List<UserRoleModel> getUserByRoleNama(String string);

    void deleteUser(UserRoleModel user);
}