package com.example.prproject.domain;

import com.example.prproject.domain.base.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "phone_numbers")
@Getter
@Setter
@Accessors(chain = true)
public class PhoneNumber extends AuditEntity {

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
