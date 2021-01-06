package uz.pdp.appg4duonotaryserver.service.team_2;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.ResPageable;
import uz.pdp.appg4duonotaryserver.payload.ServiceDto;
import uz.pdp.appg4duonotaryserver.repository.MainServiceRepository;
import uz.pdp.appg4duonotaryserver.repository.ServiceRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    MainServiceRepository mainServiceRepository;

    public ApiResponse addService(ServiceDto serviceDto) {
        try {
            serviceRepository.save(makeService(serviceDto, new uz.pdp.appg4duonotaryserver.entity.Service()));
            return new ApiResponse("Successfully added", true);
        } catch (Exception e) {
            return new ApiResponse("Error not add service", false);
        }
    }

    public ApiResponse editService(ServiceDto serviceDto) {
        try {
            Optional<uz.pdp.appg4duonotaryserver.entity.Service> optional = serviceRepository.findById(serviceDto.getId());
            if (optional.isPresent()) {
                serviceRepository.save(makeService(serviceDto, optional.get()));
                return new ApiResponse("Successfully edited", true);
            }
            return new ApiResponse("Service is not found", false);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    public ApiResponse getServicePage(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<uz.pdp.appg4duonotaryserver.entity.Service> servicePage = serviceRepository.findAll(pageable);
            return new ApiResponse("OK", true, servicePage.getContent().stream().map(this::getService).collect(Collectors.toList()), page, servicePage.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }


    public ServiceDto getService(uz.pdp.appg4duonotaryserver.entity.Service service) {
        return new ServiceDto(
                service.getId(),
                service.getName(),
                service.getMainService().getId(),
                service.getInitialCount(),
                service.getInitialSpendingTime(),
                service.getEveryCount(),
                service.getEverySpendingTime(),
                service.isDynamic(),
                service.isActive(),
                service.getChargeMinute(),
                service.getChargePercent());
    }

    public ApiResponse changeActive(UUID id) {
        try {
            uz.pdp.appg4duonotaryserver.entity.Service service = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getService", "id", id));
            service.setActive(!service.isActive());
            serviceRepository.save(service);
            return new ApiResponse(service.isActive() ? "activated" : "deactivated", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public uz.pdp.appg4duonotaryserver.entity.Service makeService(ServiceDto serviceDto, uz.pdp.appg4duonotaryserver.entity.Service service) {
        service.setMainService(mainServiceRepository.findById(serviceDto.getMainServiceId()).orElseThrow(() -> new ResourceNotFoundException("getMainService", "id", serviceDto.getMainServiceId())));
        service.setInitialCount(serviceDto.getInitialCount());
        service.setInitialSpendingTime(serviceDto.getInitialSpendingTime());
        if (serviceDto.isDynamic()) {
            service.setEveryCount(serviceDto.getEveryCount());
            service.setEverySpendingTime(serviceDto.getEverySpendingTime());
        }
        service.setDynamic(serviceDto.isDynamic());
        service.setActive(serviceDto.isActive());
        service.setChargeMinute(serviceDto.getChargeMinute());
        service.setChargePercent(serviceDto.getChargePercent());
        service.setName(serviceDto.getName());
        return service;
    }


}