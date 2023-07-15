package ru.skypro.lessons.springboot.courseworkeasyauction.dto;

import lombok.*;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Bid;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BidDTO implements Serializable {
    private String bidderName;
    private Date bidDate;

    public static BidDTO fromBid(Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidderName(bid.getBidderName());
        bidDTO.setBidDate(bid.getBidDate());
        return bidDTO;
    }
}
