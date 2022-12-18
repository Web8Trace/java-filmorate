# java-filmorate
Template repository for Filmorate project.  

![filmorate2](https://user-images.githubusercontent.com/96868763/208260797-35468cfa-a726-4486-a3c0-739f8098a5ec.png)


*Diagrams.net диаграмма*

![FILM_GENRES](https://user-images.githubusercontent.com/96868763/208261374-538de64f-d67f-4318-b23d-194b8aba197a.png)

*IntelliJ IDEA диаграмма*

### Таблица USERS

user_id - Id Пользователя  
email - почта  
login - никнейм  
name - имя пользователя  
birthday - дата рождения  

### Таблица FILMS
film_id - id Фильма  
name - название  
description - описание  
release_date - дата выхода  
rate - рэтинг  
duration - продолжительность  
mpa_id - id МПА из таблицы Mpas  

### Tаблица GENERES
genres_id - id жанра  
genre_name - имя жанра должнобыть уникальным  

### Таблица FILM_GENERES
#### Для связи фильма и жанра
id - id  
genres_id - id жанра  
film_id - id Фильма

### Таблица MPAS
mpa_id - id MPA  
mpa_name - имя (уникальное) not null  
mpa_description - описание Mpa

### Таблица FRIENDSHIP
id - id  
user_id - id пользовотеля из users  
friend_id - id друга из users  
status - статус дружбы (true - подтвеждена), уникальная пара user_id и friend_id

### Таблица LIKES
id - id  
user_id - id пользовотеля из USERS  
film_id - id фильма из FILMS, уникальная пара user_id, film_id
