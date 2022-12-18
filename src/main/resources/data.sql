MERGE INTO GENRES(genres_id, genre_name) VALUES (1, 'Комедия' );
MERGE INTO GENRES(genres_id, genre_name) VALUES (2, 'Драма' );
MERGE INTO GENRES(genres_id, genre_name) VALUES (3, 'Мультфильм' );
MERGE INTO GENRES(genres_id, genre_name) VALUES (4, 'Триллер' );
MERGE INTO GENRES(genres_id, genre_name) VALUES (5, 'Документальный' );
MERGE INTO GENRES(genres_id, genre_name) VALUES (6, 'Боевик' );
MERGE INTO MPAS(mpa_id, mpa_name, mpa_description) VALUES (1, 'G','у фильма нет возрастных ограничений' );
MERGE INTO MPAS(mpa_id, mpa_name, mpa_description) VALUES (2, 'PG','детям рекомендуется смотреть фильм с родителями' );
MERGE INTO MPAS(mpa_id, mpa_name, mpa_description) VALUES (3, 'PG-13','детям до 13 лет просмотр запрещен' );
MERGE INTO MPAS(mpa_id, mpa_name, mpa_description) VALUES (4, 'R','лицам до 17 лет просматривать фильм можно только со взрослыми' );
MERGE INTO MPAS(mpa_id, mpa_name, mpa_description) VALUES (5, 'NC-17','лицам до 18 лет просмотр запрещён' );

//При внутренем тестирование разкоментить
