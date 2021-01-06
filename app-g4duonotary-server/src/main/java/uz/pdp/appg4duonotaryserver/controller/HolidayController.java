package uz.pdp.appg4duonotaryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.HolidayDto;
import uz.pdp.appg4duonotaryserver.service.team_2.HolidayService;

import java.util.UUID;

@RestController
@RequestMapping({"/api/holiday"})
public class HolidayController {
    @Autowired
    HolidayService holidayService;

    @PostMapping
    public HttpEntity<?> saveAndEditHoliday(@RequestBody HolidayDto holidayDto) {
        ApiResponse apiResponse = holidayService.saveAndEditHoliday(holidayDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping
    public HttpEntity<?> deleteHoliday(@RequestBody HolidayDto holidayDto) {
        ApiResponse apiResponse = holidayService.deleteHoliday(holidayDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }
    @GetMapping("/list")
    public HttpEntity<?> getHolidayList(@RequestParam Integer page, @RequestParam Integer size){
        ApiResponse apiResponse = holidayService.getHolidaysList(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()? 201: 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getHoliday(@RequestParam UUID holidayId){
        ApiResponse apiResponse = holidayService.getHoliday(holidayId);
        return ResponseEntity.status(apiResponse.isSuccess()? 201: 409).body(apiResponse);
    }
}

