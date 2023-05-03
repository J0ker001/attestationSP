package com.example.attestationsp.service;


import org.springframework.http.ResponseEntity;



public interface SockAccountingService {
    ResponseEntity<?> addSocks(String color, Integer cottonPart, Integer quantity);

    ResponseEntity<?> releaseSocks(String color, Integer cottonPart, Integer quantity);

    ResponseEntity<?> remainder(String color, Integer cottonPart, String operation);

}
