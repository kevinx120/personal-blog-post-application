package com.example.blogproject.repositories;

import com.example.blogproject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository; //extension in spring.io i think
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
//Repository = "a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects".
//Teams implementing traditional Java EE patterns such as "Data Access Object" may also apply this stereotype to DAO classes