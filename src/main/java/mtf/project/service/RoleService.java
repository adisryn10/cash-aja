package mtf.project.service;

import mtf.project.model.RoleModel;

import java.util.List;

public interface RoleService {
    List<RoleModel> findAll();

    RoleModel findByNama(String nama);
}
