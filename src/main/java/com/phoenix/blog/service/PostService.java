package com.phoenix.blog.service;

import com.phoenix.blog.domain.Post;
import com.phoenix.blog.domain.User;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<Post> listRecent(int page, int size);

    Post getBySlug(String slug);

    Post create(Post post);

    int vote (User user, Long postId, short value);
}
