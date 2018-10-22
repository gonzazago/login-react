package com.react.loginreact.model;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name ="USER", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "userName"
        }),
        @UniqueConstraint(columnNames = {
                "mail"})
})
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String userName;

    @NaturalId
    @NotBlank
    @Email
    private String mail;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="USER_ROLES",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role = new HashSet<>();


    public User(@NotBlank @Size(max = 40) String name, @NotBlank @Size(max = 15) String userName,
                @NotBlank @Email String mail, @NotBlank @Size(min = 8, max = 255) String password) {
        this.name = name;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }
}
