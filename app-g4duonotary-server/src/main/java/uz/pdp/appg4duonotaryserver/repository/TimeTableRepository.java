package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appg4duonotaryserver.entity.TimeTable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@JaversSpringDataAuditable
public interface TimeTableRepository extends JpaRepository<TimeTable, UUID> {
    boolean existsAllByFromTimeAfterAndFromTimeBefore(Timestamp fromTimeBefore, Timestamp fromTimeAfter);

    boolean existsByAgentIdAndBookedTrue(UUID agent_id);

    List<TimeTable> findAllByAgentIdAndBookedTrue(UUID agent_id);

//    @Modifying
    @Query(value = "select cast(user_id as varchar ) as user_id from agent_schedule  inner join agent_schedule_hours ash on agent_schedule.id = ash.agent_schedule_id\n" +
            "inner join hour h on ash.hours_id = h.id and h.from_time<=:from_time and h.till_time>=:till_time\n" +
            "inner join agent_hour_off aho on agent_id=user_id\n" +
            "inner join time_table tt on tt.agent_id=user_id\n" +
            "where day_off = false  and week_day = (select case (select extract('dow' from date (to_date(:date,'YYYY-MM-DD'))))\n" +
            "                                                 when 0 then 'SUNDAY'\n" +
            "                                                 when 1 then 'MONDAY'\n" +
            "                                                 when 2 then 'TUESDAY'\n" +
            "                                                 when 3 then 'WEDNESDAY'\n" +
            "                                                 when 4 then 'THURSDAY'\n" +
            "                                                 when 5 then 'FRIDAY'\n" +
            "                                                 when 6 then 'SATURDAY'\n" +
            "                                                 end)\n" +
            "and (select to_char(aho.from_time,'HH24:MI') from agent_hour_off)!=:from_time and (select to_char(aho.till_time,'HH24:MI')from agent_hour_off)<=:till_time and tt.booked=false and tt.temp_booked=false and user_id in  (select user_id from user_zip_code where zip_code_id=(select id from zip_code where code=:code)) order by  (select count(booked) from time_table where agent_id = user_id) limit 1", nativeQuery = true)
    List<Object[]> getAgentFromDayOffFalseAndWeekDayAndFromTime(@Param("from_time") Timestamp  fromTime, @Param("till_time") Timestamp tillTime, @Param("date") String date, @Param("code") String zipCodeCode);
}
