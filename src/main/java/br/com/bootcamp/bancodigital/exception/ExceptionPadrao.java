package br.com.bootcamp.bancodigital.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionPadrao extends RuntimeException {
    public ExceptionPadrao(String mensagem) {
        super(mensagem);
    }
}