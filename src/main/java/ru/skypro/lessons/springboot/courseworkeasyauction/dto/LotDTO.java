package ru.skypro.lessons.springboot.courseworkeasyauction.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Lot;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class LotDTO implements Serializable {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private LotStatus status;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;

    public static LotDTO fromLot(Lot lot) {
        LotDTO lotDTO = new LotDTO();
        lotDTO.setId(lot.getId());
        lotDTO.setStatus(lot.getStatus());
        lotDTO.setTitle(lot.getTitle());
        lotDTO.setDescription(lot.getDescription());
        lotDTO.setStartPrice(lot.getStartPrice());
        lotDTO.setBidPrice(lot.getBidPrice());
        return lotDTO;
    }
}
