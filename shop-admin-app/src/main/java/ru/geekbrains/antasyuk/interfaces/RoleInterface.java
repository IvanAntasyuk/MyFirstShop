package ru.geekbrains.antasyuk.interfaces;

import ru.geekbrains.antasyuk.models.Role;

import java.util.List;
import java.util.Optional;


public interface RoleInterface {

    List<Role> findAll();

    void save(Role role);

    void deleteById(Long id);

    Optional<Role> findById(Long id);

}
