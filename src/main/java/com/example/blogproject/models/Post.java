package com.example.blogproject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*; //Jakarta Persistence is the API for the management for persistence and object/relational mapping.
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter //these two have to do with lom-dock which i think automates getters and setters variables for our class
@Setter
public class Post {

    @Id //Specifies the primary key of an entity. The field or property to which the Id annotation is applied should be one of the following types: any Java primitive type
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //The GeneratedValue annotation may be applied to a primary key property or field of an entity or mapped superclass in conjunction with the Id annotation.
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")  //Specifies the mapped column for a persistent property or field---- The SQL fragment that is used when generating the DDL for the column.----(DDL) is a computer language used to create and modify the structure of database objects in a database.
    private String body;        //store body content of our blog post

    private LocalDateTime createdAt;     //this shows exactly something was created at

    private LocalDateTime updatedAt; //i guess when you last updated the post


    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account; //all were doing here is creating a many to one relationship form post to account and were gonna be joining the two tables with the help of the actual account id referencing the id column onn that object

    @Override
    public String toString() {
        return "Post{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", body='" + body + "'" +
            ", createdAt='" + createdAt + "'" +
            ", updatedAt='" + updatedAt + "'" +
            "}";
    }
}
