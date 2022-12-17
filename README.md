# java-filmorate
Template repository for Filmorate project.  

![](../../../Documents/filmorate2.png)

*Diagrams.net диаграмма*

![](../../FILM_GENRES.png)
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