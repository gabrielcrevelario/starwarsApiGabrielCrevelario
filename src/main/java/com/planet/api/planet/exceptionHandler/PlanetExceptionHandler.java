package com.planet.api.planet.exceptionHandler;

import com.planet.api.planet.services.exception.NonExistPlanetException;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class PlanetExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        String messageUser = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> erros = Arrays.asList(new Error(messageUser, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest request) {

        String messageUser = messageSource.getMessage("resource.operation-not-allowed", null,
                LocaleContextHolder.getLocale());
        String messageDevelopment = ex.toString();
        List<Error> erros = Arrays.asList(new Error(messageUser, messageDevelopment));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NonExistPlanetException.class})
    public ResponseEntity<Object> handlerNonExistPlanetException(NonExistPlanetException ex,
                                                                            WebRequest request) {
        String messageUser = messageSource.getMessage("planet.nonexistent", null,
                LocaleContextHolder.getLocale());
        String messageDevelopment = ex.toString();
        List<Error> erros = Arrays.asList(new Error(messageUser, messageDevelopment));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> error = createListErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> createListErrors(BindingResult bindingResult) {
        List<Error> erros = new ArrayList<>();

        for (FieldError fielError : bindingResult.getFieldErrors()) {
            String messageUser = messageSource.getMessage(fielError, LocaleContextHolder.getLocale());
            String messageDevelopment = fielError.toString();
            erros.add(new Error(messageUser, messageDevelopment));
        }
        return erros;
    }
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                       WebRequest request) {

        String messageUser = messageSource.getMessage("resource.not-found", null,
                LocaleContextHolder.getLocale());
        String messageDevelopment = ex.toString();
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDevelopment));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    public static class Error {

        private String messageUser;
        private String messageDevelopment;

        public Error(String messageUser, String messageDevelopment) {
            this.messageUser = messageUser;
            this.messageDevelopment = messageDevelopment;
        }

        public String GetMessageDevelopment() {
            return messageUser;
        }

        public String getMessageDevelopment() {
            return messageDevelopment;
        }

    }
}