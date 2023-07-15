package ru.skypro.lessons.springboot.courseworkeasyauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    @Query(value = "select * from bid " +
            "WHERE lot_id = :id " +
            "order by bid_date desc " +
            "limit 1 ",
            nativeQuery = true)
    Bid getLastBid(Integer id);
}
