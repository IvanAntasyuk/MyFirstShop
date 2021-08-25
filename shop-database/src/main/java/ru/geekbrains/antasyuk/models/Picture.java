package ru.geekbrains.antasyuk.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contentType", nullable = false)
    private String contentType;

    @Column(name = "storageUUID")
    private String storageUUID;

    @ManyToOne
    private Product product;

    public Picture(Long id, String name, String contentType, String storageUUID, Product product) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.storageUUID = storageUUID;
        this.product = product;
    }

    public Picture() {
    }
}
