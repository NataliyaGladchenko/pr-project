package com.example.prproject.domain;

import com.example.prproject.domain.base.AuditEntity;
import com.example.prproject.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class User extends AuditEntity {

    @NotBlank
    @Length(max = 100)
    @Column(name = "password")
    private String password;

    @NotBlank
    @Length(max = 100)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Length(max = 255)
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Size(max = 20)
    private Set<PhoneNumber> phoneNumbers;

    public User(String login, String password) {
        this
                .setEmail(login)
                .setPassword(password);
    }

}
