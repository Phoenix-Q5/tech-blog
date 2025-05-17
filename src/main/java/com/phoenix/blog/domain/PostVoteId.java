package com.phoenix.blog.domain;

import java.io.Serializable;

// composite key class
public record PostVoteId(Long user, Long post) implements Serializable {
}
