package uz.pdp.appg4duonotaryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.MainService;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.MainServiceDto;
import uz.pdp.appg4duonotaryserver.repository.MainServiceRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MainServiceService {

    @Autowired
    MainServiceRepository mainServiceRepository;

    public ApiResponse saveAndEditMainService(MainServiceDto dto) {
        try {
            MainService saved = mainServiceRepository.save(makeMainService(dto));
            return new ApiResponse(dto.getId() != null ? "Successfully edited" : "Successfully Saved", true, saved);
        } catch (Exception e) {
            return new ApiResponse("error", false);
        }
    }

    public ApiResponse getMainService(UUID id) {
        MainService mainService = mainServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getMainService", "getid", id));
        return new ApiResponse("OK", true, getMainServiceDto(mainService));
    }

    public MainServiceDto getMainServiceDto(MainService mainService) {
        return new MainServiceDto(
                mainService.getId(),
                mainService.getName(),
                mainService.getFromTime(),
                mainService.getTillTime(),
                mainService.isOnline(),
                mainService.getOrderNumber(),
                mainService.isActive(),
                mainService.getDescription()
        );
    }

    public ApiResponse getMainServiceList(int page, int size) {
        Page<MainService> mainServices = mainServiceRepository.findAll(CommonUtils.getPageable(page, size));
        return new ApiResponse("OK", true, mainServices.getContent().stream().map(this::getMainServiceDto).collect(Collectors.toList()));
    }

    public ApiResponse deleteMainService(UUID id) {
        try {
            mainServiceRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in delete", false);
        }
    }

    public MainService makeMainService(MainServiceDto dto) {
        MainService mainService = new MainService();
        if (dto.getId() != null) {
            Optional<MainService> byId = mainServiceRepository.findById(dto.getId());
            if (byId.isPresent()) {
                mainService = byId.get();
            }
        }
        if (mainServiceRepository.existsByFromTimeAndTillTimeAndName(dto.getFromTime(), dto.getTillTime(), dto.getName())) {
            mainService = mainServiceRepository.findByFromTimeAndTillTimeAndName(dto.getFromTime(), dto.getTillTime(), dto.getName());
        } else {
            mainService.setName(dto.getName());
        }
        mainService.setActive(dto.isActive());
        mainService.setDescription(dto.getDescription());
        mainService.setFromTime(dto.getFromTime());
        mainService.setTillTime(dto.getTillTime());
        mainService.setOnline(dto.isOnline());
        mainService.setOrderNumber(dto.getOrderNumber());
        return mainService;
    }
}
