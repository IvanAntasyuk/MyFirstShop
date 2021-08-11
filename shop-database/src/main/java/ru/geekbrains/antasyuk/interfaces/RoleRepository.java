package ru.geekbrains.antasyuk.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.antasyuk.models.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
