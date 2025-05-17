-- ===== USERS ==============================================================
CREATE TABLE users (
                       id           BIGSERIAL PRIMARY KEY,
                       google_sub   VARCHAR(60) UNIQUE NOT NULL,
                       name         VARCHAR(120)       NOT NULL,
                       email        VARCHAR(160) UNIQUE,
                       avatar_url   TEXT,
                       created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===== TAGS ===============================================================
CREATE TABLE tags (
                      id   BIGSERIAL PRIMARY KEY,
                      name VARCHAR(32) UNIQUE NOT NULL
);

-- ===== POSTS ==============================================================
CREATE TABLE posts (
                       id            BIGSERIAL PRIMARY KEY,
                       slug          VARCHAR(160) UNIQUE NOT NULL,
                       title         VARCHAR(200)        NOT NULL,
                       body          TEXT                NOT NULL,
                       cover_image   TEXT,
                       published_at  TIMESTAMP           NOT NULL,
                       updated_at    TIMESTAMP           NOT NULL,
                       author_id     BIGINT              NOT NULL REFERENCES users(id),
                       up_votes      INT  DEFAULT 0,
                       down_votes    INT  DEFAULT 0
);

CREATE INDEX ix_posts_published   ON posts (published_at DESC);
CREATE INDEX ix_posts_search_gin  ON posts USING gin (to_tsvector('simple', title || ' ' || body));

-- ===== POST-TAGS (many-to-many) ==========================================
CREATE TABLE post_tags (
                           post_id BIGINT REFERENCES posts(id) ON DELETE CASCADE,
                           tag_id  BIGINT REFERENCES tags(id)  ON DELETE CASCADE,
                           PRIMARY KEY (post_id, tag_id)
);

-- ===== COMMENTS ==========================================================
CREATE TABLE comments (
                          id          BIGSERIAL PRIMARY KEY,
                          body        TEXT       NOT NULL,
                          created_at  TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          author_id   BIGINT     NOT NULL REFERENCES users(id),
                          post_id     BIGINT     NOT NULL REFERENCES posts(id)
);

-- ===== VOTES (1 user ⇢ 1 post) ===========================================
CREATE TABLE post_votes (
                            user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                            value   SMALLINT NOT NULL CHECK (value IN (-1, 1)),
                            PRIMARY KEY (user_id, post_id)
);