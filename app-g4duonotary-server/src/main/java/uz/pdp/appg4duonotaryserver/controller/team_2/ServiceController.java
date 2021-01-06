package uz.pdp.appg4duonotaryserver.controller.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.entity.Service;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.ServiceDto;
import uz.pdp.appg4duonotaryserver.repository.ServiceRepository;
import uz.pdp.appg4duonotaryserver.service.team_2.ServiceService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    ServiceService service;

    @Autowired
    ServiceRepository serviceRepository;

    @PostMapping
    public HttpEntity<?> addService(@RequestBody ServiceDto serviceDto) {
        ApiResponse apiResponse = service.addService(serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/editService")
    public HttpEntity<?> editService(@RequestBody ServiceDto serviceDto) {
        ApiResponse apiResponse = service.editService(serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getService(@PathVariable UUID id) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getService"));
        return ResponseEntity.ok(this.service.getService(service));
    }


    @GetMapping("/getPage")
    public HttpEntity<?> getServicePage(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                        @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(service.getServicePage(page, size));
    }

    @GetMapping("/changeActive")
    public  HttpEntity<?> changeActive(@RequestParam UUID id){
        ApiResponse apiResponse = service.changeActive(id);
        return  ResponseEntity.ok(apiResponse);
    }


}