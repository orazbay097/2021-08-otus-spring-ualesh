insert into authors(surname, name)
values ('Ремарк', 'Эрих'),
       ('Толстой', 'Лев'),
       ('Роулинг', 'Джоан'),
       ('Ауезов', 'Мухтар');

insert into genres(name)
values ('проза'),
       ('роман'),
       ('фантастика');

insert into books(name, author_id)
values ('Гарри Поттер и философский камень', 3),
       ('Три товарища', 1);

insert into book_genres(book_id, genre_id)
values (1, 3),
       (2, 1);