package ru.skypro.lessons.springboot.courseworkeasyauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.courseworkeasyauction.model.Lot;

@Repository
public interface LotRepository extends JpaRepository<Lot, Integer> {

}
