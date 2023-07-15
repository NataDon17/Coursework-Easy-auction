package ru.skypro.lessons.springboot.courseworkeasyauction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.courseworkeasyauction.dto.*;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Bid;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Lot;
import ru.skypro.lessons.springboot.courseworkeasyauction.repository.BidRepository;
import ru.skypro.lessons.springboot.courseworkeasyauction.repository.LotRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    @Override
    public void createLot(LotCreate lotCreate) {
        lotRepository.save(lotCreate.toLot());
    }

    @Override
    public ResponseEntity<?> startLot(Integer id) throws IOException {
        Lot lot = lotRepository.findById(id).orElseThrow(IOException::new);
        if (lot.getStatus() != LotStatus.valueOf("STARTED")) {
            lot.setStatus(LotStatus.valueOf("STARTED"));
            lotRepository.save(lot);
            return new ResponseEntity<>("Лот переведен в статус начато", HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> stopLot(Integer id) throws IOException {
        Lot lot = lotRepository.findById(id).orElseThrow(IOException::new);
        if (lot.getStatus() != LotStatus.valueOf("STOPPED")) {
            lot.setStatus(LotStatus.valueOf("STOPPED"));
            lotRepository.save(lot);
            return new ResponseEntity<>("Лот перемещен в статус остановлен", HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.OK);
    }

    public Integer getCurrentPrice(Integer id) throws IOException {
        Lot lot = lotRepository.findById(id).orElseThrow(IOException::new);
        int countBidByLot = (int) bidRepository.findAll().stream()
                .filter(bid -> bid.getLot().getId().equals(lot.getId()))
                .count();
        return lot.getBidPrice() * countBidByLot + lot.getStartPrice();
    }

    @Override
    public LotFullInfo getFullLot(Integer id) throws IOException {
        Lot lot = lotRepository.findById(id).orElseThrow(IOException::new);
        BidDTO bidDTO = BidDTO.fromBid(bidRepository.findAll().stream()
                .filter(bid -> bid.getLot().getId().equals(lot.getId()))
                .sorted(Comparator.comparing(Bid::getBidderName))
                .max(Comparator.comparing(Bid::getBidDate))
                .orElse(new Bid("не найден")));
        LotFullInfo lotFullInfo = new LotFullInfo();
        lotFullInfo.setId(lot.getId());
        lotFullInfo.setStatus(lot.getStatus());
        lotFullInfo.setTitle(lot.getTitle());
        lotFullInfo.setDescription(lot.getDescription());
        lotFullInfo.setStartPrice(lot.getStartPrice());
        lotFullInfo.setBidPrice(lot.getBidPrice());
        lotFullInfo.setCurrentPrice(getCurrentPrice(id));
        lotFullInfo.setLastBid(bidDTO);
        return lotFullInfo;
    }

    @Override
    public Collection<LotDTO> findLots(Integer page, LotStatus status) {
        Page<Lot> lotPage = lotRepository
                .findAll(PageRequest.of(page, 10));
        Collection<Lot> lotList = lotPage.stream()
                .filter(lot -> lot.getStatus().equals(status))
                .toList();
        return lotList.stream()
                .map(LotDTO::fromLot)
                .collect(Collectors.toList());
    }

    @Override
    public List<LotFromExport> listAll() {
        List<LotFromExport> lotList = lotRepository.findAll().stream()
                .map(LotFromExport::fromLot)
                .peek(lotFullInfo -> {
                    try {
                        lotFullInfo.setCurrentPrice(getCurrentPrice(lotFullInfo.getId()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        lotList.forEach(l -> l.setLastBid(bidRepository.getLastBid(l.getId())));
        return lotList;
    }
}
