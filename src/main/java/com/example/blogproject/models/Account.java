package com.example.blogproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private String password;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "account") //one too many is for every one object we can create we can link that object to many other objects of what were linking too in this case a user can login and can have a bunch of posts that all belonng to one or each user.
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER) //this means that each user can have multiple authorities attached to it
    @JoinTable(name = "account_authority",
        joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},  //semantics
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public String toString() {
        return "Account{" +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", email='" + email + "'" +
            ", authorities=" + authorities +
            "}";
    }

}
//need to create a relationship with account and authority
//called this class account instead of user because spring security calls and uses user with some of its properties