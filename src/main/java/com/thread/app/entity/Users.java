package com.thread.app.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Serdeable
@Entity
@Table(name = "Users")
public class Users {
    @Id
    private Long id;

    private String username;
    private String password;
    private String email;

}
