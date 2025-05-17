package com.phoenix.blog.service;

import com.phoenix.blog.domain.Comment;
import com.phoenix.blog.domain.User;
import org.springframework.data.domain.Page;

public interface CommentService {

    Page<Comment> list(Long postId, int page, int size);
    Comment add(User user, Long postId, String body);
}
