package com.phoenix.blog.dto;

import java.time.Instant;
import java.util.List;

public record PostDTO(
        Long id,
        String slug,
        String title,
        String body,
        String coverImage,
        int upVotes,
        int downVotes,
        Instant publishedAt,
        String authorName,
        String authorAvatarUrl,
        List<String> tags
) {}