package com.phoenix.blog.service.impl;

import com.phoenix.blog.domain.*;
import com.phoenix.blog.exception.PostNotFoundException;
import com.phoenix.blog.respository.PostRepository;
import com.phoenix.blog.respository.PostVoteRepository;
import com.phoenix.blog.respository.TagRepository;
import com.phoenix.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostVoteRepository postVoteRepository;

    @Override
    public Page<Post> listRecent(int page, int size) {
        return postRepository.findRecent(PageRequest.of(page, size));
    }

    @Override
    public Post getBySlug(String slug) {
        return postRepository.findBySlug(slug)
                .orElseThrow(() -> new PostNotFoundException(slug));
    }

    @Transactional
    @Override
    public Post create(Post post) {
        post.setSlug(slugify(post.getTitle()));
        post.setPublishedAt(Instant.now());
        post.setUpdatedAt(Instant.now());
        post.setTags(post.getTags().stream()
                .map(t -> tagRepository.findByNameIgnoreCase(t.getName())
                        .orElseGet(() -> tagRepository.save(t)))
                .collect(Collectors.toSet()));

        return postRepository.save(post);
    }

    @Transactional
    @Override
    public int vote(User user, Long postId, short value) {
        Post post = postRepository.getReferenceById(postId);
        PostVoteId id = new PostVoteId(user.getId(), postId);

        postVoteRepository.findById(id).ifPresentOrElse(v -> {
            if (v.getValue() == value) postVoteRepository.delete(v);
            else v.setValue(value);
        }, () -> {
            PostVote nv = new PostVote();
            nv.setUser(user); nv.setPost(post); nv.setValue(value);
            postVoteRepository.save(nv);
        });
        return postVoteRepository.score(postId);
    }

    private static String slugify(String title) {
        return title.toLowerCase(Locale.ROOT)
                .replaceAll("[^\\w]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
