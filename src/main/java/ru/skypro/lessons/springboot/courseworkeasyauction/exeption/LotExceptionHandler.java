package ru.skypro.lessons.springboot.courseworkeasyauction.exeption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.lessons.springboot.courseworkeasyauction.service.LotService;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class LotExceptionHandler {
    Logger logger = LoggerFactory.getLogger(LotService.class);

    @ExceptionHandler
    public ResponseEntity<?> handleIOException(IOException ioException) {
        String message = "Лот не найден";
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(SQLException sqlException) {
        String message = "Внутренняя ошибка сервера";
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exception) {
        String message = "Лот в неверном статусе";
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
