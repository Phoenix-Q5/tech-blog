package com.phoenix.blog.respository;

import com.phoenix.blog.domain.PostVoteId;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.phoenix.blog.domain.PostVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface PostVoteRepository  extends JpaRepository<PostVote, PostVoteId> {
    Optional<PostVote> findByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT COALESCE(SUM(v.value), 0) FROM PostVote v WHERE v.post.id = :postId")
    int score(@Param("postId") Long postId);
}
