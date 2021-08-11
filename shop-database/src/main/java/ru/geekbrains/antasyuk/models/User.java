package ru.geekbrains.antasyuk.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer age;

    @NotBlank
    @Column(length = 512,nullable = false)
    private String username;

    @Column
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String username, String password, Integer age, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }


}
