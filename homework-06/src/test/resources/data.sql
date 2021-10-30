insert into authors(surname, name)
values ('s1', 'n1'),
       ('s2', 'n2'),
       ('s3', 'n3'),
       ('s4', 'n4'),
       ('s5', 'n5');

insert into genres(name)
values ('g1'),
       ('g2'),
       ('g3'),
       ('g4'),
       ('g5');

insert into comments(text)
values ('c1'),
       ('c2'),
       ('c3'),
       ('c4'),
       ('c5');

insert into books(name, author_id)
values ('b1', 1),
       ('b2', 2),
       ('b3', 3),
       ('b4', 4),
       ('b5', 5);

insert into book_genres(book_id, genre_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);