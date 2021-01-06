package uz.pdp.appg4duonotaryserver.service.team_2;

import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.ServicePrice;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.ServiceDto;
import uz.pdp.appg4duonotaryserver.payload.ServicePriceDto;
import uz.pdp.appg4duonotaryserver.repository.ServicePriceRepository;
import uz.pdp.appg4duonotaryserver.repository.ServiceRepository;
import uz.pdp.appg4duonotaryserver.repository.ZipCodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ServicePriceService {

    private final ServicePriceRepository servicePriceRepository;
    private final ServiceRepository serviceRepository;
    private final ZipCodeRepository zipCodeRepository;

    public ServicePriceService(ServicePriceRepository servicePriceRepository, ServiceRepository serviceRepository, ZipCodeRepository zipCodeRepository) {
        this.servicePriceRepository = servicePriceRepository;
        this.serviceRepository = serviceRepository;
        this.zipCodeRepository = zipCodeRepository;
    }

    /**
     * Service pricelani save or edit qilish
     * 4ta xolat uchun yozilgan
     */
    public ApiResponse saveOrEditServicePrice(ServicePriceDto servicePriceDto) {
        try {
            List<ZipCode> zipCodes = new ArrayList<>();
            if (servicePriceDto.isAllZipCodes()) {
                zipCodes = zipCodeRepository.findAll();
            } else {
                if (servicePriceDto.getZipCodeIds() != null) {
                    zipCodes = zipCodeRepository.findAllById(servicePriceDto.getZipCodeIds());
                } else if (servicePriceDto.getCountyIds() != null) {
                    zipCodes = zipCodeRepository.findAllByCountyIdIn(servicePriceDto.getCountyIds());
                } else if (servicePriceDto.getStateIds() != null) {
                    zipCodes = zipCodeRepository.findAllByCountyStateIdIn(servicePriceDto.getStateIds());
                }
            }
            uz.pdp.appg4duonotaryserver.entity.Service service = serviceRepository.findById(servicePriceDto.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("service", "Id", servicePriceDto.getServiceId()));
            for (ZipCode zipCode : zipCodes) {
                ServicePrice servicePrice = new ServicePrice();
                if (servicePriceRepository.existsByZipCodeAndService(zipCode, service)) {
                    servicePrice = servicePriceRepository.findByZipCodeAndService(zipCode, service);
                } else {
                    servicePrice.setZipCode(zipCode);
                    servicePrice.setService(service);
                }
                servicePrice.setPrice(servicePriceDto.getPrice());
                servicePrice.setChargeMinute(servicePriceDto.getChargeMinute());
                servicePrice.setChargePercent(servicePriceDto.getChargePercent());
                servicePrice.setActive(servicePriceDto.isActive());
                servicePriceRepository.save(servicePrice);
            }
            return new ApiResponse("OK", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    public ApiResponse getServicePriceDtoList() {
        List<ServicePriceDto> servicePriceDtoList = new ArrayList<>();
        List<Object[]> minMaxPriceAndActiveGroupByServiceId =
                servicePriceRepository.getMinMaxPriceAndActiveGroupByServiceId();
        for (Object[] objects : minMaxPriceAndActiveGroupByServiceId) {
            ServicePriceDto dto = new ServicePriceDto();
            dto.setServiceDto(getServiceDto(serviceRepository.findById((UUID.fromString((String) objects[0]))).get()));
            dto.setMinPrice(Double.parseDouble(objects[1].toString()));
            dto.setMaxPrice(Double.parseDouble(objects[2].toString()));
            dto.setActive(Integer.parseInt(objects[3].toString()) > 0);
//            uz.pdp.appg4duonotaryserver.entity.Service byId = serviceRepository.findById(dto.getServiceId())
//                    .orElseThrow(() -> new ResourceNotFoundException("","",dto.getServiceId()));
//            ServiceDto serviceDto = new ServiceDto();
//            serviceDto.setName(byId.getName());
//            dto.setServiceDto(serviceDto);
            servicePriceDtoList.add(dto);
        }
        return new ApiResponse("OK",true,servicePriceDtoList);
    }

    public ServiceDto getServiceDto(uz.pdp.appg4duonotaryserver.entity.Service service){
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
                service.getChargePercent()
        );
    }
}