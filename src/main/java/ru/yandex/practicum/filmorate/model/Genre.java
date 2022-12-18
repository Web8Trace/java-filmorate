package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Genre implements Comparable<Genre> {
   final int id;
   final String name;

   @Override
   public int compareTo(Genre o) {

      return Integer.compare(this.id, o.id);
   }
}
