package ru.skypro.lessons.springboot.courseworkeasyauction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "bid")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bidderName;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidDate;
    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    public Bid(String bidderName) {
        this.bidderName = bidderName;
    }

    @Override
    public String toString() {
        return "Bid{" +
                " bidderName: " + bidderName +
                ", bidDate: " + bidDate +
                '}';
    }
}
