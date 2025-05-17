package com.phoenix.blog.service.impl;

import com.phoenix.blog.domain.Comment;
import com.phoenix.blog.domain.User;
import com.phoenix.blog.respository.CommentRepository;
import com.phoenix.blog.respository.PostRepository;
import com.phoenix.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    @Override
    public Page<Comment> list(Long postId, int page, int size) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId, PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public Comment add(User user, Long postId, String body) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setAuthor(user);
        comment.setPost(postRepository.getReferenceById(postId));
        return commentRepository.save(comment);
    }
}
