package mtf.project.service;

import mtf.project.model.RoleModel;
import mtf.project.repository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDb roleDb;

    public List<RoleModel> findAll(){
        return roleDb.findAll();
    }

    @Override
    public RoleModel findByNama(String nama) {
        return roleDb.findByNama(nama);
    }
}
