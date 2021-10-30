create table authors
(
    id      bigserial primary key,
    surname varchar(255) not null,
    name    varchar(255) not null
);

create table genres
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table comments
(
    id   bigserial primary key,
    text varchar(255) not null
);

create table books
(
    id        bigserial primary key,
    name      varchar(255) not null,
    author_id bigint references authors (id)
);


create table book_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id),
    primary key (book_id, genre_id)
);


create table book_comments
(
    book_id    bigint references books (id) on delete cascade,
    comment_id bigint  unique references comments (id) on delete cascade,
    primary key (book_id, comment_id)
);