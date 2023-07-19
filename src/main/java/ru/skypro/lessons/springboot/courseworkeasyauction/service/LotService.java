package ru.skypro.lessons.springboot.courseworkeasyauction.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.courseworkeasyauction.dto.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface LotService {
    void createLot(LotCreate lotCreate);

    ResponseEntity<?> startLot(Integer id) throws IOException;

    ResponseEntity<?> stopLot(Integer id) throws IOException;

    LotFullInfo getFullLot(Integer id) throws IOException;

    Collection<LotDTO> findLots(Integer page, LotStatus status);

    List<LotFromExport> listAll();
}
