package ru.skypro.lessons.springboot.courseworkeasyauction.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import ru.skypro.lessons.springboot.courseworkeasyauction.dto.*;
import ru.skypro.lessons.springboot.courseworkeasyauction.service.BidService;
import ru.skypro.lessons.springboot.courseworkeasyauction.service.LotService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lot")
@RequiredArgsConstructor
public class AuctionController {
    private final LotService lotService;
    private final BidService bidService;
    Logger logger = LoggerFactory.getLogger(LotService.class);

    @PostMapping("/")
    public void createLot(@RequestBody LotCreate lotCreate) {
        logger.debug("Create lot {}", lotCreate);
        logger.info("Was invoked method for create lot " + lotCreate);
        lotService.createLot(lotCreate);
    }

    @GetMapping("/{id}/first")
    public BidDTO getFirstBidder(@PathVariable Integer id) throws IOException {
        logger.error("Lot {} not found", id);
        return bidService.getFirstBidder(id);
    }

    @GetMapping("/{id}/frequent")
    public BidDTO getMostFrequentBidder(@PathVariable Integer id) throws IOException {
        logger.error("Lot {} not found", id);
        return bidService.getMostFrequentBidder(id);
    }

    @GetMapping("/{id}")
    public LotFullInfo getFullLot(@PathVariable Integer id) throws IOException {
        logger.error("Lot {} not found", id);
        return lotService.getFullLot(id);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<?> startLot(@PathVariable Integer id) throws IOException {
        logger.debug("Started lot {}", id);
        logger.info("Was invoked method start bidding on a lot {}", id);
        logger.error("Lot {} not found", id);
        return lotService.startLot(id);
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<?> stopLot(@PathVariable Integer id) throws IOException {
        logger.debug("Stopped lot {}", id);
        logger.info("Was invoked method stop bidding on a lot {}", id);
        logger.error("Lot {} not found", id);
        return lotService.stopLot(id);
    }

    @PostMapping("/{id}/bid")
    public ResponseEntity<?> createBid(@PathVariable Integer id,
                                       @RequestBody String name) throws IOException {
        logger.debug("Create a bid for a lot {}", id);
        logger.info("Was invoked method create a bid for a lot {}", id);
        logger.error("Lot {} in the wrong status", id);
        logger.error("Lot {} not found", id);
        return bidService.createBid(id, name);
    }

    @GetMapping("/")
    public Collection<LotDTO> findLots(@RequestParam("page") Integer page,
                                       @ModelAttribute("status") LotStatus status) {
        logger.debug("Create list of lots in the status {} on the page {} ", status, page);
        logger.info("Was invoked method create list of lots in the status {} on the page {} ", status, page);
        return lotService.findLots(page, status);
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        logger.debug("Create file csv");
        logger.info("Was invoked method get file csv");
        logger.error("Unknown server error");
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=allLots_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
        List<LotFromExport> listAllLots = lotService.listAll();
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvTitle = {"Lot id", "Title", "Status", "Last Bidder", "Current Price"};
        String[] csvContent = {"id", "title", "status", "lastBid", "currentPrice"};
        csvWriter.writeHeader(csvTitle);
        for (LotFromExport l : listAllLots) {
            csvWriter.write(l, csvContent);
        }
        csvWriter.close();
    }
}
