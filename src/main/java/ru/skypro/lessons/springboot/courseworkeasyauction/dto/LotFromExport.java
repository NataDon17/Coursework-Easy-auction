package ru.skypro.lessons.springboot.courseworkeasyauction.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Bid;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Lot;

@NoArgsConstructor
@Getter
@Setter
public class LotFromExport {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private LotStatus status;
    private String title;
    private int currentPrice;
    private Bid lastBid;

    public static LotFromExport fromLot(Lot lot) {
        LotFromExport lotFromExport = new LotFromExport();
        lotFromExport.setId(lot.getId());
        lotFromExport.setStatus(lot.getStatus());
        lotFromExport.setTitle(lot.getTitle());
        return lotFromExport;
    }
}
