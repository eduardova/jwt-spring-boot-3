package dev.eduardova.jwt.dtos.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SimpleMessage {

    private final String message;
    private String details;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    public SimpleMessage(String message, String details, LocalDateTime date) {
        this.message = message;
        this.details = details;
        this.date = date;
    }

    public SimpleMessage(String message, LocalDateTime date) {
        this(message, null, date);
    }
    
    public SimpleMessage(String message) {
        this(message, LocalDateTime.now());
    }
}
