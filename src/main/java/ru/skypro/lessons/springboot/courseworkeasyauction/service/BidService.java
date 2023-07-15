package ru.skypro.lessons.springboot.courseworkeasyauction.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.courseworkeasyauction.dto.BidDTO;

import java.io.IOException;

public interface BidService {
    BidDTO getFirstBidder(Integer id) throws IOException;

    BidDTO getMostFrequentBidder(Integer id) throws IOException;

    ResponseEntity<?> createBid(Integer id, String name) throws IOException;
}
