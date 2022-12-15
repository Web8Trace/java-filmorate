package ru.yandex.practicum.filmorate.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Map;

@RestControllerAdvice("ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response checkUserEmail(ValidationException e) {
        return new Response();
    }


    @ExceptionHandler({FilmNotFoundException.class, UserNotFoundException.class, MpaNotFoundException.class,
            GenreNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFoundHendler(NotFoundException e) {
        return Map.of("error", e.getMessage(), e.getParm(), e.getValue());
    }

    @ExceptionHandler({UserAlreadyException.class, FileAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> alreadyExist(AlreadyException e) {
        return Map.of("error", e.getMessage(), e.getParm(), e.getValue());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> ConstraintViolation(ConstraintViolationException e) {
        return Map.of("Error", e.getMessage());
    }

}