package mtf.project.service;

import mtf.project.model.RoleModel;
import mtf.project.model.UserRoleModel;
import mtf.project.repository.RoleDb;
import mtf.project.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDb userDb;

    @Autowired
    private RoleDb roleDb;

    @Override
    public List<UserRoleModel> getAllUser() {
        return userDb.findAll();
    }

    @Override
    public UserRoleModel updateUser(UserRoleModel user) {
        UserRoleModel userUpdated = userDb.findById(user.getId());
        userUpdated.setName(user.getName());
        userUpdated.setUsername(user.getUsername());
        userUpdated.setLatestAuthor(user.getLatestAuthor());
        userUpdated.setLatestEdit(user.getLatestEdit());
        return userDb.save(userUpdated);
    }

    @Override
    public UserRoleModel getUserById(String id) {
        return userDb.findById(id);
    }

    @Override
    public UserRoleModel addUser(UserRoleModel user) {
        String pass = encrypt(user.getPassword());
        RoleModel roleCustomer = roleDb.findByNama("ADMIN");
        user.setPassword(pass);
        user.setRole(roleCustomer);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public List<UserRoleModel> getUserByRoleNama(String string) {
        return userDb.findByRoleNama(string);
    }

    @Override
    public void deleteUser(UserRoleModel user) {
        System.out.println("user : " + user.getUsername());
        userDb.delete(user);
    }

    @Override
    public UserRoleModel getUserByUsername(String username) {
        return userDb.findByUsername(username);
    }
}