package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
@Transactional
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(@Lazy PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }


    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDao.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    public List<User> showAllUser() {
        return userDao.findAllUsers();
    }



    public void update(User user) {
        User updateUser = entityManager.find(User.class, user.getId());

        if (updateUser == null) {
            throw new RuntimeException("User not found with id: " + user.getId());
        }

        // Обновляем поля пользователя
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        updateUser.setEmail(user.getEmail());
        updateUser.setAge(user.getAge());

        // Обновляем пароль только если он был изменен
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Обновляем роли
        for (Role role : user.getRoles()) {
            if (!updateUser.getRoles().contains(role)) {
                updateUser.getRoles().add(role);
            }
        }

        // Сохраняем пользователя через EntityManager
        entityManager.merge(updateUser);
    }
    public void delete(int id) {
        userDao.deleteById(id);
    }


    public void saveUser(User user) {
        userDao.save(user);
    }

    public User findUserById(Integer id) {
        return userDao.findById(id);
    }


}

