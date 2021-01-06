package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appg4duonotaryserver.entity.AgentSchedule;
import uz.pdp.appg4duonotaryserver.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@JaversSpringDataAuditable
public interface AgentScheduleRepository extends JpaRepository<AgentSchedule, UUID> {
    Optional<AgentSchedule> findByUser(User user);
    Page<AgentSchedule> findAllByUserId(UUID user_id, Pageable pageable);

//    @Query(value = "select day_off, week_day, user_id, (select count(booked) from time_table where agent_id = user_id)from agent_schedule\n" +
//            "where day_off = false and week_day = (select case (select extract('dow' from date :date))\n" +
//            "                             when 0 then 'SUNDAY'\n" +
//            "                             when 1 then 'MONDAY'\n" +
//            "                             when 2 then 'TUESDAY'\n" +
//            "                             when 3 then 'WEDNESDAY'\n" +
//            "                             when 4 then 'THURSDAY'\n" +
//            "                             when 5 then 'FRIDAY'\n" +
//            "                             when 6 then 'SATURDAY'\n" +
//            "                             end)\n" +
//            "and user_id in (select user_id from user_zip_code where active = true and zip_code_id = :zip_code_id)order by id limit 1",nativeQuery = true)
//    List<Object[]> getByDateAndZipcode(@Param("date") Date date,@Param("zip_code_id") UUID zip_code_id);

    @Query(value = "select * from agent_schedule where day_off = false and week_day = (select case (select extract('dow' from date (to_date(:date,'YYYY-MM-DD'))))\n" +
            "when 0 then 'SUNDAY'\n" +
            "when 1 then 'MONDAY'\n" +
            "when 2 then 'TUESDAY'\n" +
            "when 3 then 'WEDNESDAY'\n" +
            "when 4 then 'THURSDAY'\n" +
            "when 5 then 'FRIDAY'\n" +
            "when 6 then 'SATURDAY'\n" +
            "end)\n" +
            "and user_id in (select user_id from user_zip_code where active = true and zip_code_id in(select zip_code_id from service_price where id = :servicePriceId and active))",nativeQuery = true)
    List<AgentSchedule> getAgentScheduleBySelectedDate(@Param("date") String date,@Param("servicePriceId") UUID servicePriceId);
}
