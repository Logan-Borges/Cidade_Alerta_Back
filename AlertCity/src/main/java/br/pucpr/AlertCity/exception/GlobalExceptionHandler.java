package br.pucpr.AlertCity.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaCadastradoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse handleEmail(EmailJaCadastradoException ex, HttpServletRequest request) {

        return ErroResponse.builder()
                .status(400)
                .erro(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResponse handleNotFound(UsuarioNaoEncontradoException ex, HttpServletRequest request) {

        return ErroResponse.builder()
                .status(404)
                .erro(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponse handleGeral(Exception ex, HttpServletRequest request) {

        return ErroResponse.builder()
                .status(500)
                .erro("Erro interno no servidor")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }
}