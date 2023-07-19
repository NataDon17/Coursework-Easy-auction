package ru.skypro.lessons.springboot.courseworkeasyauction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Lot;

import java.io.Serializable;
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LotCreate implements Serializable {
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;

    public Lot toLot() {
        Lot lot=new Lot();
        lot.setTitle(this.getTitle());
        lot.setDescription(this.getDescription());
        lot.setStartPrice(this.getStartPrice());
        lot.setBidPrice(this.getBidPrice());
        return lot;
    }
}
