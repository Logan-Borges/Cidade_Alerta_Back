package br.pucpr.AlertCity.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErroResponse {

    private int status;
    private String erro;
    private String path;
    private LocalDateTime timestamp;
}