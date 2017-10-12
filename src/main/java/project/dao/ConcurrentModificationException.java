package project.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Исключение в случае конкурентого изменения объекта в БД. */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConcurrentModificationException extends RuntimeException {

}
