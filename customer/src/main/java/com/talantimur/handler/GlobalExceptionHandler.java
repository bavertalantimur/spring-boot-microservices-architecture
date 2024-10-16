package com.talantimur.handler;

import com.talantimur.exception.CustomerNotFoundException; // Müşteri bulunamadı istisnasını içe aktarma

import org.springframework.http.HttpStatus; // HTTP durum kodlarını içe aktarma
import org.springframework.http.ResponseEntity; // HTTP yanıtını temsil eden sınıfı içe aktarma
import org.springframework.validation.FieldError; // Alan hatalarını temsil eden sınıfı içe aktarma
import org.springframework.web.bind.MethodArgumentNotValidException; // Geçersiz metod argümanı istisnasını içe aktarma
import org.springframework.web.bind.annotation.ExceptionHandler; // İstisna işleyici anotasyonunu içe aktarma
import org.springframework.web.bind.annotation.RestControllerAdvice; // Denetleyici tavsiyesini içe aktarma

import java.util.HashMap; // HashMap sınıfını içe aktarma

import static org.springframework.http.HttpStatus.BAD_REQUEST; // BAD_REQUEST durum kodunu içe aktarma

@RestControllerAdvice // Bu sınıfın, tüm denetleyicilere tavsiye sağladığını belirtir
public class GlobalExceptionHandler {

    // Müşteri bulunamadığında tetiklenecek istisna işleyici
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handle(CustomerNotFoundException exp) {
        // HTTP yanıtı oluşturma: 404 Not Found durumu ve hata mesajı ile
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // Durum kodunu 404 olarak ayarlama
                .body(exp.getMsg()); // Hata mesajını yanıt gövdesi olarak döner
    }

    // Geçersiz metod argümanı durumunda tetiklenecek istisna işleyici
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        var errors = new HashMap<String, String>(); // Hata mesajlarını saklamak için bir HashMap oluşturma

        // Geçersiz argümanların hata listesini alma
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField(); // Hatanın ait olduğu alan adı
                    var errorMessage = error.getDefaultMessage(); // Hata mesajı
                    errors.put(fieldName, errorMessage); // Alan adını ve hata mesajını haritaya ekleme
                });

        // HTTP yanıtı oluşturma: 400 Bad Request durumu ve hata bilgileri ile
        return ResponseEntity
                .status(BAD_REQUEST) // Durum kodunu 400 olarak ayarlama
                .body(new ErrorResponse(errors)); // Hata mesajlarını içeren ErrorResponse nesnesini yanıt gövdesi olarak döner
    }
}
