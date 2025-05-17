package com.phoenix.blog.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_votes")
@Getter
@Setter
@NoArgsConstructor
@IdClass(PostVoteId.class)
public class PostVote {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * +1 (up-vote)  or −1 (down-vote)
     */
    private short value;
}

