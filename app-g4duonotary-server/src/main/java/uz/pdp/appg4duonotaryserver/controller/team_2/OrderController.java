package uz.pdp.appg4duonotaryserver.controller.team_2;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.AgentBookedDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.FreeTimeDto;
import uz.pdp.appg4duonotaryserver.payload.TestDto;
import uz.pdp.appg4duonotaryserver.service.TimeTableService;
import uz.pdp.appg4duonotaryserver.service.team_2.OrderService;

import java.util.List;
import java.util.UUID;

@RestController("api/order")
public class OrderController {

    final
    OrderService orderService;
    final TimeTableService timeTableService;

    public OrderController(OrderService orderService, TimeTableService timeTableService) {
        this.orderService = orderService;
        this.timeTableService = timeTableService;
    }


    @PostMapping
    HttpEntity<?> TempBookThisTime(@RequestBody AgentBookedDto agentBookedDto) {
        ApiResponse apiResponse = timeTableService.tempBookedAgent(agentBookedDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    /**
     * Bu method tanlangan zipcodedagi servicelarni narxini olb beradi
     */
    @GetMapping
    HttpEntity<?> chooseServicePrice(UUID servicePriceId) {
        ApiResponse apiResponse = orderService.chooseServicePrice(servicePriceId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOne(id));
    }


    @GetMapping("/priceByCount")
    public HttpEntity<?> get(@RequestParam(value = "servicePriceId") UUID servicePriceId,
                             @RequestParam(value = "count") Integer count) {
        ApiResponse apiResponse = orderService.getPriceByCount(servicePriceId, count);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/test")
    public List<FreeTimeDto> TimesForNewOrder(@RequestBody TestDto dto) {
        return timeTableService.getTimeTableToOrder(dto.getDate(), dto.getServicePriceId(), dto.getCount());

    }
}
