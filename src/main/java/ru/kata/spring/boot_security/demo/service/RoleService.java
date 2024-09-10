package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class RoleService  {


    private final RoleDao roleDao;



    public RoleService(RoleDao roleDao) {

        this.roleDao = roleDao;

    }


    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

    public Role findByName(String name) {
        return roleDao.findByName(name);
    }


    public void saveRole(Role role) {
        roleDao.save(role);
    }

}

