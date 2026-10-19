package by.kvarta.controller;

import by.kvarta.dto.LeadRequest;
import by.kvarta.entity.Lead;
import by.kvarta.service.LeadService;
import by.kvarta.service.LeadService.DuplicateLeadException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LeadController {
    private final LeadService service;

    public LeadController(LeadService service) {
        this.service = service;
    }

    @PostMapping("/lead")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LeadRequest request) {
        Lead lead = service.create(request);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Спасибо! Заявка отправлена. Мы свяжемся с вами.");
        response.put("id", lead.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(DuplicateLeadException.class)
    public ResponseEntity<Map<String, String>> duplicate(DuplicateLeadException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badPhone(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalid(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
        String message = error == null ? "Проверьте заполнение формы." : error.getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("message", message));
    }
}
