package com.example.backendokimmo.models;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "users")
public class User extends BaseEntity
{
    private String name;

    @Column(unique = true)
    private String email;

    private String password;
}
