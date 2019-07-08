package me.chen.eztech.service;

import me.chen.eztech.model.Role;
import me.chen.eztech.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role create(String name, String description){
        Role role = new Role();
        role.setDescription(description);
        role.setName(name);

        return roleRepository.save(role);
    }
}
