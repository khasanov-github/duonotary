package uz.pdp.appg4duonotaryserver.service.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.*;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.*;
import uz.pdp.appg4duonotaryserver.repository.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MainServiceWorkTimeService {

    @Autowired
    ZipCodeRepository zipCodeRepository;

    @Autowired
    MainServiceWorkTimeRepository mainServiceWorkTimeRepository;

    @Autowired
    MainServiceRepository mainServiceRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServicePriceRepository servicePriceRepository;

    @Autowired
    CountyRepository countyRepository;

    public ApiResponse addAndEditMainServiceWorkTime(MainServiceWorkTimeDto dto) {
        try {
            MainServiceWorkTime newMainServiceWT = new MainServiceWorkTime();
            if (dto.isOnline()) {
                if (dto.getId() != null) {
                    Optional<MainServiceWorkTime> optional = mainServiceWorkTimeRepository.findById(dto.getId());
                    if (optional.isPresent()) {
                        MainServiceWorkTime mainServiceWorkTime = optional.get();
                        mainServiceWorkTime.setMainService(mainServiceRepository.findById(dto.getMainServiceId() != null ? dto.getMainServiceId() : dto.getMainServiceDto().getId()).orElseThrow(() -> new ResourceNotFoundException("", "", dto.getMainServiceId())));
//                            MainService mainService = mainServiceRepository.findById(dto.getMainServiceDto().getId()).orElseThrow(() -> new ResourceNotFoundException("", "", dto.getMainServiceDto().getId()));
//                            boolean fromTill = mainServiceRepository.existsByFromTimeAndTillTime(mainService.getFromTime(), mainService.getTillTime());
                        boolean check = mainServiceWorkTimeRepository.existsByFromAndTillTime(dto.getFromTime(), dto.getTillTime());
                        if (check) {
                            return new ApiResponse("error. Time does not match in database", false);
                        }
                        mainServiceWorkTime.setFromTime(dto.getFromTime());
                        mainServiceWorkTime.setTillTime(dto.getTillTime());

                        mainServiceWorkTime.setPercent(dto.getPercent());
                        mainServiceWorkTime.setActive(dto.isActive());
                        mainServiceWorkTimeRepository.save(mainServiceWorkTime);
                        return new ApiResponse("Edited", true);
                    } else {
                        return new ApiResponse("Not found", false);
                    }

                }
                newMainServiceWT.setMainService(mainServiceRepository.findById(dto.getMainServiceId() != null ? dto.getMainServiceId() : dto.getMainServiceDto().getId()).orElseThrow(() -> new ResourceNotFoundException("", "", dto.getMainServiceId())));
                newMainServiceWT.setFromTime(dto.getFromTime());
                newMainServiceWT.setTillTime(dto.getTillTime());
                newMainServiceWT.setPercent(dto.getPercent());
                newMainServiceWT.setActive(dto.isActive());
                mainServiceWorkTimeRepository.save(newMainServiceWT);
            } else {
                List<ZipCode> zipCodes;
                if (dto.isAllZipCodes()) {
                    zipCodes = zipCodeRepository.findAll();
                } else {
                    if (dto.getZipCodeIds() != null) {
                        zipCodes = zipCodeRepository.findAllById(dto.getZipCodeIds());
                    } else if (dto.getCountyIds() != null) {
                        zipCodes = zipCodeRepository.findAllByCountyIdIn(dto.getCountyIds());
                    } else if (dto.getStateIds() != null) {
                        zipCodes = zipCodeRepository.findAllByCountyStateIdIn(dto.getStateIds());
                    } else
                        return new ApiResponse("Selected nothing", false);
                }
                MainService mainService = mainServiceRepository.findById(dto.getMainServiceId()).orElseThrow(() -> new ResourceNotFoundException("mainService", "getId", dto.getMainServiceId()));
                for (ZipCode zipCode : zipCodes) {
                    MainServiceWorkTime mainServiceWorkTime = new MainServiceWorkTime();
                    if (mainServiceWorkTimeRepository.existsByZipCodeAndMainService(zipCode, mainService)) {
                        mainServiceWorkTime = mainServiceWorkTimeRepository.findByZipCodeAndMainService(zipCode, mainService);
                    } else {
                        mainServiceWorkTime.setZipCode(zipCode);
                        mainServiceWorkTime.setMainService(mainService);
                    }
                    mainServiceWorkTime.setFromTime(dto.getFromTime());
                    mainServiceWorkTime.setTillTime(dto.getTillTime());
                    mainServiceWorkTime.setPercent(dto.getPercent());
                    mainServiceWorkTime.setActive(dto.isActive());
                    mainServiceWorkTimeRepository.save(mainServiceWorkTime);
                }
            }
            return new ApiResponse(dto.getId() != null ? "Edited" : "Saved", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
//        return new ApiResponse("Error", false);
    }

    //    public boolean checkTime(List<MainServiceWorkTimeDto> dtos) {
//        for (MainServiceWorkTimeDto dtoTime : dtos) {
//            if (dtoTime.getFromTime().isAfter(dtoTime.getTillTime())) {
//                return false;
//            } else {
//                for (MainServiceWorkTimeDto dtoTime2 : dtos) {
//                    if (dtoTime.getFromTime() == dtoTime.getFromTime() && dtoTime.getTillTime() == dtoTime2.getTillTime()) {
//
//                    } else {
//                        if (dtoTime2.getFromTime().isAfter(dtoTime.getFromTime()) && dtoTime2.getFromTime().isBefore(dtoTime.getTillTime()) ||
//                                dtoTime2.getFromTime().isBefore(dtoTime.getFromTime()) && dtoTime2.getTillTime().isAfter(dtoTime.getTillTime()) ||
//                                dtoTime2.getTillTime().isAfter(dtoTime.getFromTime()) && dtoTime2.getTillTime().isBefore(dtoTime.getTillTime())) {
//                            return false;
//                        }
//                    }
//                }
//            }
//        }
//        return true;
//    }
    public ApiResponse deleteMainServiceWorkTime(UUID id) {
        try {
            mainServiceWorkTimeRepository.deleteById(id);
            return new ApiResponse("Successfully delete", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleted ", false);
        }
    }

    public ApiResponse changeActive(UUID id) {
        try {
            MainServiceWorkTime mainServiceWorkTime = mainServiceWorkTimeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getMainServiceWorkTime", "id", id));
            mainServiceWorkTime.setActive(!mainServiceWorkTime.isActive());
            mainServiceWorkTimeRepository.save(mainServiceWorkTime);
            return new ApiResponse("OK", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Error", false);
        }
    }

//    public MainServiceWorkTime makeMainServiceWorktime(MainServiceWorkTimeDto dto, MainServiceWorkTime
//            mainServiceWorkTime) {
//        mainServiceWorkTime.setMainService(mainServiceRepository.findById(dto.getMainServiceId()
//                != null ? dto.getMainServiceId() : dto.getMainServiceDto().getId()).orElseThrow(() ->
//                new ResourceNotFoundException("getMainServiceDto", "getId", dto.getMainServiceDto().getId())));
//        mainServiceWorkTime.setFromTime((dto.getFromTime()));
//        mainServiceWorkTime.setTillTime(dto.getTillTime());
//        mainServiceWorkTime.setPercent(dto.getPercent());
//        mainServiceWorkTime.setActive(dto.isActive());
//        return mainServiceWorkTime;
//    }

    public MainServiceWorkTimeDto getMainServiceWorkTime(MainServiceWorkTime mainServiceWorkTime) {
        return new MainServiceWorkTimeDto(
                mainServiceWorkTime.getId(),
                getMainService(mainServiceWorkTime.getMainService()),
                mainServiceWorkTime.getFromTime(),
                mainServiceWorkTime.getTillTime(),
                mainServiceWorkTime.getPercent(),
                mainServiceWorkTime.isActive(),
                mainServiceWorkTime.getMainService().isOnline(),
                getZipCodeDto(mainServiceWorkTime.getZipCode() != null ? mainServiceWorkTime.getZipCode() : new ZipCode())
//                getZipCodeDto(mainServiceWorkTime.getZipCode())
        );
    }

    public MainServiceDto getMainService(MainService mainService) {
        return new MainServiceDto(
                mainService.getId(),
                mainService.getName(),
                (mainService.getFromTime()),
                (mainService.getTillTime()),
                mainService.isOnline(),
                mainService.getOrderNumber(),
                mainService.isActive(),
                mainService.getDescription()
        );
    }

    public ZipCodeDto getZipCodeDto(ZipCode zipCode) {
        return new ZipCodeDto(
                zipCode.getId(),
                zipCode.getCode()
        );
    }

    public ApiResponse getPageable(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MainServiceWorkTime> mainServiceWorkTime = mainServiceWorkTimeRepository.findAll(pageable);
            return new ApiResponse(
                    new ResPageable(page,
                            size,
                            mainServiceWorkTime.getTotalPages(),
                            mainServiceWorkTime.getTotalElements(),
                            getMainServiceWorkTimeDto(mainServiceWorkTime.getContent())));
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }


    public List<MainServiceWorkTimeDto> getMainServiceWorkTimeDto
            (List<MainServiceWorkTime> mainServiceWorkTimes) {
        return mainServiceWorkTimes.stream().map(this::getMainServiceWorkTime).collect(Collectors.toList());
    }

    public ApiResponse getMinMaxPercent(String search) {
        List<MainServiceWorkTimeDto> mainServiceWorkTimeDtos = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
        if (search.equals("all")) {
            List<Object[]> minMaxPriceAndActiveTrueAndOnlineFalse = mainServiceWorkTimeRepository.getMinMaxPriceAndActiveTrueAndOnlineFalse();
            for (Object[] objects : minMaxPriceAndActiveTrueAndOnlineFalse) {
                MainServiceWorkTimeDto dto = new MainServiceWorkTimeDto();
                dto.setMainServiceDto(getMainService(mainServiceRepository.findById((UUID.fromString((String) objects[0]))).get()));
                dto.setFromTime((LocalTime.parse(objects[1].toString())));
                dto.setTillTime((LocalTime.parse(objects[2].toString())));
                dto.setMinPercent(Double.valueOf(objects[3].toString()));
                dto.setMaxPercent(Double.valueOf(objects[4].toString()));
                dto.setActive(Integer.parseInt(objects[5].toString()) > 0);
                mainServiceWorkTimeDtos.add(dto);
            }
            List<Object[]> byMainServiceOnline = mainServiceWorkTimeRepository.getMinMaxPriceAndActiveTrueAndOnlineTrue();
            for (Object[] objects : byMainServiceOnline) {
                MainServiceWorkTimeDto dto = new MainServiceWorkTimeDto();
                dto.setMainServiceDto(getMainService(mainServiceRepository.findById((UUID.fromString((String) objects[0])) ).orElseThrow(() -> new ResourceNotFoundException("getId","",objects[0]))));
                dto.setFromTime((LocalTime.parse(objects[1].toString())));
                dto.setTillTime((LocalTime.parse(objects[2].toString())));
                dto.setMinPercent(Double.valueOf(objects[3].toString()));
                dto.setMaxPercent(Double.valueOf(objects[4].toString()));
                dto.setActive(Integer.parseInt(objects[5].toString()) > 0);
                mainServiceWorkTimeDtos.add(dto);
            }
        } else {
            Optional<MainServiceWorkTime> byZipCodeCode = mainServiceWorkTimeRepository.findByZipCodeCode(search);
            if (byZipCodeCode.isPresent()) {
                MainServiceWorkTime mainServiceWorkTime = byZipCodeCode.get();
                MainServiceWorkTimeDto dto = new MainServiceWorkTimeDto();
                dto.setId(mainServiceWorkTime.getId());
                dto.setActive(mainServiceWorkTime.isActive());
                dto.setPercent(mainServiceWorkTime.getPercent());
                dto.setZipCodeDto(getZipCodeDto(mainServiceWorkTime.getZipCode()));
                mainServiceWorkTimeDtos.add(dto);
            } else {
                return new ApiResponse("Error", false);
            }
        }
        return new ApiResponse("OK", true, mainServiceWorkTimeDtos);
    }

    public StateDto getStateDto(State state) {
        return new StateDto(
                state.getId(),
                state.getName()
        );
    }

    public CountyDto getCountyDto(County county) {
        return new CountyDto(
                county.getId(),
                county.getName(),
                getStateDto(county.getState())
        );
    }

    public List<CountyDto> getCountiesByState(UUID stateId) {
        return countyRepository.findAllByStateId(stateId).stream().map(this::getCountyDto).collect(Collectors.toList());
    }

    public List<ZipCodeDto> getZipCodesByCountyId(UUID countyId) {
        return zipCodeRepository.findAllByServiceIdAndCountyId(countyId).stream().map(this::getZipCodeDto).collect(Collectors.toList());
    }


//    public List<ZipCodeDto> getZipCodesByServiceIdAndCountyId(UUID countyId) {
//        return zipCodeRepository.findAllByServiceIdAndCountyId(countyId).stream().map(this::getZipCodeDto).collect(Collectors.toList());
//    }
}




