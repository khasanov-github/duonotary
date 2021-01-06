package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.MainService;
import uz.pdp.appg4duonotaryserver.entity.MainServiceWorkTime;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface MainServiceWorkTimeRepository extends JpaRepository<MainServiceWorkTime, UUID> {
    boolean existsByZipCodeAndMainService(ZipCode zipCode, MainService mainService);

    MainServiceWorkTime findByZipCodeAndMainService(ZipCode zipCode, MainService mainService);

    @Query(value = "\n" +
            "select exists(select * from main_service as ts where (ts.from_time > ts.till_time  and\n" +
            "                                                          (ts.from_time < :fromTime or\n" +
            "                                                           ts.till_time > :fromTime or\n" +
            "                                                           ts.from_time < :tillTime or\n" +
            "                                                           ts.till_time > :tillTime)) or (\n" +
            "        ts.from_time < ts.till_time and\n" +
            "        (ts.from_time < :fromTime and ts.till_time >:fromTime)or\n" +
            "        (ts.from_time < :tillTime and ts.till_time >:tillTime))or\n" +
            "(select exists(select * from main_service_work_time as bs where (bs.from_time > bs.till_time  and\n" +
            "                                                           (bs.from_time < :fromTime or\n" +
            "                                                            bs.till_time > :fromTime or\n" +
            "                                                            bs.from_time < :tillTime or\n" +
            "                                                            bs.till_time > :tillTime)) or (\n" +
            "        bs.from_time < bs.till_time and\n" +
            "        (bs.from_time < :fromTime and bs.till_time >:fromTime)or\n" +
            "        (bs.from_time < :tillTime and bs.till_time >:tillTime)))))\n", nativeQuery = true)
    boolean existsByFromAndTillTime(LocalTime fromTime, LocalTime tillTime);

    @Query(value = "select Cast(main_service_id as varchar) main_service_id, from_time, till_time, coalesce(min(percent),0)as minPercent,coalesce(max(percent),0)as maxPercent, count(active)as activeCount\n" +
            "from main_service_work_time where main_service_id in (select id from main_service where online=false) and active=true group by from_time,till_time,main_service_id", nativeQuery = true)
    List<Object[]> getMinMaxPriceAndActiveTrueAndOnlineFalse();

    @Query(value = "select Cast(main_service_id as varchar) main_service_id, from_time, till_time, coalesce(min(percent),0)as minPercent,coalesce(max(percent),0)as maxPercent, count(active)as activeCount\n" +
            "from main_service_work_time where main_service_id in (select id from main_service where online) and active=true group by from_time,till_time,main_service_id", nativeQuery = true)
    List<Object[]> getMinMaxPriceAndActiveTrueAndOnlineTrue();

    Optional<MainServiceWorkTime> findByMainServiceOnline(boolean mainService_online);

    Optional<MainServiceWorkTime> findByZipCodeCode(String zipCode_code);
}
