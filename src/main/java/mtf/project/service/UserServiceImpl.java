package mtf.project.service;

import mtf.project.model.*;
import mtf.project.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public UserRoleModel getUserById(String id) {
        return userDb.findById(id);
    }

    @Override
    public UserRoleModel addUser(UserRoleModel user) {
        String pass = encrypt(user.getPassword());
        RoleModel roleCustomer = roleDb.findByNama("CUSTOMER");
        user.setPassword(pass);
        user.setRole(roleCustomer);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<UserRoleModel> getUserByRoleNama(String string) {
        return userDb.findByRoleNama(string);
    }

    @Override
    public UserRoleModel getUserByUsername(String username) {
        return userDb.findByUsername(username);
    }
}