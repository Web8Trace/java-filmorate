package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
@Getter
public class NotFoundException extends NoSuchElementException {
  private   final String parm;
  private   final String value;

  public NotFoundException(String s, String parm, String value) {
    super(s);
    this.parm = parm;
    this.value = value;
  }
}
