package ru.skypro.lessons.springboot.courseworkeasyauction.model;

import jakarta.persistence.*;
import lombok.*;
import ru.skypro.lessons.springboot.courseworkeasyauction.dto.LotStatus;

@Entity
@Table(name = "lot")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;
    @Enumerated(EnumType.STRING)
    private LotStatus status = LotStatus.valueOf("CREATED");
    private int currentPrice;
}
