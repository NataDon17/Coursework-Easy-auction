package ru.skypro.lessons.springboot.courseworkeasyauction.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LotFullInfo {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private LotStatus status;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;
    private int currentPrice;
    private BidDTO lastBid;
}
