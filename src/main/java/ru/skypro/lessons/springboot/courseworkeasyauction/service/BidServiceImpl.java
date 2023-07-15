package ru.skypro.lessons.springboot.courseworkeasyauction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.courseworkeasyauction.dto.BidDTO;
import ru.skypro.lessons.springboot.courseworkeasyauction.dto.LotStatus;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Bid;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Lot;
import ru.skypro.lessons.springboot.courseworkeasyauction.repository.BidRepository;
import ru.skypro.lessons.springboot.courseworkeasyauction.repository.LotRepository;

import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;
    private final LotRepository lotRepository;

    @Override
    public BidDTO getFirstBidder(Integer id) throws IOException {
        Lot lot = lotRepository.findById(id).orElseThrow(IOException::new);
        return BidDTO.fromBid(bidRepository.findAll().stream()
                .filter(bid -> bid.getLot().getId().equals(lot.getId()))
                .min(Comparator.comparing(Bid::getBidDate))
                .orElse(new Bid("не найден")));
    }

    @Override
    public BidDTO getMostFrequentBidder(Integer id) throws IOException {
        Lot lot = lotRepository.findById(id).orElseThrow(IOException::new);
        return BidDTO.fromBid(bidRepository.findAll().stream()
                .filter(bid -> bid.getLot().getId().equals(lot.getId()))
                .sorted(Comparator.comparing(Bid::getBidderName))
                .max(Comparator.comparing(Bid::getBidDate))
                .orElse(new Bid("не найден")));
    }

    @Override
    public ResponseEntity<?> createBid(Integer id, String name) throws IOException {
        Lot lot = lotRepository.findById(id).orElseThrow(IOException::new);
        if (lot.getStatus() == LotStatus.valueOf("STARTED")) {
            Bid bid = new Bid();
            bid.setLot(lot);
            bid.setBidDate(bid.getBidDate());
            bid.setBidderName(name);
            bidRepository.save(bid);
            return new ResponseEntity<>("Ставка создана", HttpStatus.OK);
        } else return new ResponseEntity<>("Лот в неверном статусе", HttpStatus.BAD_REQUEST);
    }
}
