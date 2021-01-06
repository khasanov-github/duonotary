package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.TimeDuration;

public interface TimeDurationRepository extends JpaRepository<TimeDuration, Integer> {
    @Query(value = "select * from time_duration order by id desc limit 1", nativeQuery = true)
    TimeDuration getLastDurationTime();
}
