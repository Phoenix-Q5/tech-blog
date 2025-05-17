package com.phoenix.blog.model;

import java.time.Instant;
import java.util.List;

public record UserPost(
        Long id,
        String slug,
        String title,
        String body,
        int upVotes,
        int downVotes,
        String authorName,
        String authorAvatarUrl,
        Instant publishedAt,
        List<String> tags
) {}
