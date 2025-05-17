package com.phoenix.blog.respository;

import com.phoenix.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findBySlug(String slug);

    @Query("SELECT p FROM Post p WHERE p.publishedAt IS NOT NULL")
    @EntityGraph(attributePaths = "tags")
    Page<Post> findRecent(Pageable page);

    @Query("""
        SELECT p FROM Post p
        JOIN p.tags t
        WHERE LOWER(t.name) = LOWER(:tag)
          AND p.publishedAt IS NOT NULL
        """)
    Page<Post> findByTag(@Param("tag") String tag, Pageable page);
}