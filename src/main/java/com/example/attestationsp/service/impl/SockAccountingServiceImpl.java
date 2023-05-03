package com.example.attestationsp.service.impl;

import com.example.attestationsp.model.Socks;
import com.example.attestationsp.repository.SocksRepository;
import com.example.attestationsp.service.SockAccountingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;


@Service
public class SockAccountingServiceImpl implements SockAccountingService {

    private final Logger logger = LoggerFactory.getLogger(SockAccountingServiceImpl.class);
    private final SocksRepository socksRepository;

    public SockAccountingServiceImpl(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    @Override
    public ResponseEntity<?> addSocks(String color, Integer cottonPart, Integer quantity) {
        logger.info("Was invoked method to add socks by color={}, cottonPart={}, quantity={}",
                color, cottonPart, quantity);

        Socks socks;

        if (color != null && cottonPart > 0 && cottonPart < 101 && quantity > 0) {
            socks = new Socks(color, cottonPart, quantity);
        } else return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");

        Socks bdSocks = socksRepository.getSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());

        if (bdSocks != null &&
                socks.getCottonPart().equals(bdSocks.getCottonPart()) && socks.getColor().equals(bdSocks.getColor())) {
            bdSocks.setQuantity(bdSocks.getQuantity() + socks.getQuantity());
            socksRepository.save(bdSocks);
            return ResponseEntity.ok().body("удалось добавить приход");
        } else {
            socksRepository.save(socks);
            return ResponseEntity.ok().body("удалось добавить новый приход");
        }
    }

    @Override
    public ResponseEntity<?> releaseSocks(String color, Integer cottonPart, Integer quantity) {
        logger.info("Was invoked method to release socks by color={}, cottonPart={}, quantity={}",
                color, cottonPart, quantity);

        Socks bdSocks = socksRepository.getSocksByColorAndCottonPart(color, cottonPart);

        if (bdSocks != null && bdSocks.getQuantity() >= quantity) {
            int oldQuantity = bdSocks.getQuantity();
            bdSocks.setQuantity(oldQuantity - quantity);
            socksRepository.save(bdSocks);
            return ResponseEntity.ok().body("реализовали: " + quantity + " остаток: " + bdSocks.getQuantity());
        } else return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");
    }


    @Override
    public ResponseEntity<?> remainder(String color, Integer cottonPart, String operation) {
        logger.info("Was invoked method to release socks by color={}, cottonPart={}, operation={}",
                color, cottonPart, operation);

        if (color != null && cottonPart != null && operation == null) {

            return ResponseEntity.ok().
                    body(socksRepository.getSocksByColorAndCottonPart(color, cottonPart).getQuantity());
        }

        else if (color != null && cottonPart != null && operation.equals("moreThan")) {
            return ResponseEntity.ok().
                    body(socksRepository.findAll().stream().filter(s -> s.getColor().equals(color)).
                    filter(s -> s.getCottonPart() > cottonPart).map(Socks::getQuantity).
                            flatMapToInt(IntStream::of).sum());

        }

        else if (color != null && cottonPart != null && operation.equals("lessThan")) {
            return ResponseEntity.ok().body(socksRepository.findAll().stream().
                    filter(s -> s.getColor().equals(color)).filter(s -> s.getCottonPart() < cottonPart).
                    map(Socks::getQuantity).flatMapToInt(IntStream::of).sum());

        }   else return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");

    }
}

