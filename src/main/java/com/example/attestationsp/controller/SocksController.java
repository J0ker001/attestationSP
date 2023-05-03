package com.example.attestationsp.controller;


import com.example.attestationsp.service.SockAccountingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/socks")
public class SocksController {

    private final SockAccountingService sockAccountingService;

    public SocksController(SockAccountingService sockAccountingService) {
        this.sockAccountingService = sockAccountingService;
    }

    @Operation(summary = "Добавить новый товар на склад или обновить существующий")
    @PostMapping("/income")
    public ResponseEntity<?> addSocks(@RequestParam String color, @RequestParam Integer cottonPart, @RequestParam Integer quantity) {
        return sockAccountingService.addSocks(color, cottonPart, quantity);
    }

    @Operation(summary = "Списать нужное кол-во товара со склада")
    @PostMapping("/outcome")
    public ResponseEntity<?> releaseSocks(@RequestParam String color, @RequestParam Integer cottonPart, @RequestParam Integer quantity) {
        return sockAccountingService.releaseSocks(color, cottonPart, quantity);
    }

    @Operation(summary = "Все остаток товара, по заданным параметрам")
    @GetMapping
    public ResponseEntity<?> allSocks(@RequestParam(required = false) String color,
                                   @RequestParam(required = false) Integer cottonPart,
                                   @RequestParam(required = false) String operation
    ) {
        return sockAccountingService.remainder(color, cottonPart, operation);
    }
}
