package uz.pdp.appg4duonotaryserver.service.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.AdditionalService;
import uz.pdp.appg4duonotaryserver.entity.AdditionalServicePrice;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.AdditionalServiceDto;
import uz.pdp.appg4duonotaryserver.payload.AdditionalServicePriceDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.repository.AdditionalServicePriceRepository;
import uz.pdp.appg4duonotaryserver.repository.AdditionalServiceRepository;
import uz.pdp.appg4duonotaryserver.repository.ServiceRepository;
import uz.pdp.appg4duonotaryserver.repository.ZipCodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdditionalServicePriceService {

    @Autowired
    AdditionalServicePriceRepository additionalServicePriceRepository;
    @Autowired
    AdditionalServiceRepository additionalServiceRepository;
    @Autowired
    ZipCodeRepository zipCodeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public ApiResponse saveOrEditAdditionalServicePrice(AdditionalServicePriceDto additionalServicePriceDto) {
        try {
            List<ZipCode> zipCodes = new ArrayList<>();
            if (additionalServicePriceDto.isAllZipCodes()) {
                zipCodes = zipCodeRepository.findAll();
            } else if (additionalServicePriceDto.getZipCodeIds() != null) {
                zipCodes = zipCodeRepository.findAllById(additionalServicePriceDto.getZipCodeIds());
            } else if (additionalServicePriceDto.getCountyIds() != null) {
                zipCodes = zipCodeRepository.findAllByCountyIdIn(additionalServicePriceDto.getCountyIds());
            } else if (additionalServicePriceDto.getStateIds() != null) {
                zipCodes = zipCodeRepository.findAllByCountyStateIdIn(additionalServicePriceDto.getStateIds());
            }
            AdditionalService additionalService = additionalServiceRepository.findById(additionalServicePriceDto.getAdditionalServiceId()).orElseThrow(() -> new ResourceNotFoundException("getAdditionalService", "getId", additionalServicePriceDto.getAdditionalServiceId()));
            for (ZipCode zipCode : zipCodes) {
                AdditionalServicePrice additionalServicePrice = new AdditionalServicePrice();
                if (additionalServicePriceRepository.existsByZipCodeAndAdditionalService(zipCode, additionalService)) {
                    Optional<AdditionalServicePrice> byZipCodeAndAdditionalService = additionalServicePriceRepository.findByZipCodeAndAdditionalService(zipCode, additionalService);
                    if (byZipCodeAndAdditionalService.isPresent()){
                        additionalServicePrice = byZipCodeAndAdditionalService.get();
                    }
                } else {
                    additionalServicePrice.setZipCode(zipCode);
                    additionalServicePrice.setAdditionalService(additionalService);
                }
                additionalServicePrice.setPrice(additionalServicePriceDto.getPrice());
                additionalServicePrice.setActive(additionalServicePriceDto.isActive());
                additionalServicePriceRepository.save(additionalServicePrice);
            }
            return new ApiResponse("Ok", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

//    public AdditionalServicePrice makeAdditionalServicePrice(AdditionalServicePrice additionalServicePrice, AdditionalService additionalService, Double price) {
//        additionalServicePrice.setAdditionalService(additionalService);
//        additionalServicePrice.setPrice(price);
//        additionalServicePrice.setActive(true);
//        return additionalServicePrice;
//    }


//    public AdditionalServicePriceDto getAdditionalServicePriceDto(AdditionalServicePrice additionalServicePrice) {
//        AdditionalServicePriceDto additionalServicePriceDto = new AdditionalServicePriceDto();
//        additionalServicePriceDto.setAdditionalServiceDto(getAdditionalServiceDto(additionalServicePrice.getAdditionalService()));
//        additionalServicePriceDto.setPrice(additionalServicePrice.getPrice());
//        additionalServicePriceDto.setActive(additionalServicePrice.isActive());
//        return additionalServicePriceDto;
//    }
//
//    public AdditionalServiceDto getAdditionalServiceDto(AdditionalService additionalService) {
//        return new AdditionalServiceDto(
//                additionalService.getName(),
//                additionalService.isActive(),
//                additionalService.getDescription(),
//                null
//        );
//    }


//    public ApiResponse getAdditionalServicePricePage(int page, int size) {
//        try {
//            Page<AdditionalServicePrice> additionalServicePricePage = additionalServicePriceRepository.findAll(CommonUtils.getPageable(page, size));
//            return new ApiResponse("ok", true, additionalServicePricePage.getContent().stream().map(this::getAdditionalServicePriceDto).collect(Collectors.toList()), page, additionalServicePricePage.getTotalElements());
//        } catch (IllegalArgumentException e) {
//            return new ApiResponse("error", false);
//        }
//    }

    public ApiResponse deleteAdditionalServicePrice(UUID id) {
        try {
            additionalServicePriceRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("error", false);

        }
    }

    public ApiResponse getAdditionalServicePriceDtoList() {
        List<AdditionalServicePriceDto> additionalServicePriceDtoList = new ArrayList<>();
        List<Object[]> additionalServicePriceGroupByMinMaxPrice = additionalServicePriceRepository.getAdditionalServicePriceGroupByMinMaxPrice();
        for (Object[] objects : additionalServicePriceGroupByMinMaxPrice) {
            AdditionalServicePriceDto dto = new AdditionalServicePriceDto();
            dto.setAdditionalServiceId(UUID.fromString(objects[0].toString()));
            dto.setMinPrice(Double.parseDouble(objects[2].toString()));
            dto.setMaxPrice(Double.parseDouble(objects[3].toString()));
            dto.setActive(Integer.parseInt(objects[4].toString()) > 0);
            AdditionalServiceDto additionalServiceDto=new AdditionalServiceDto();
            additionalServiceDto.setName(objects[1].toString());
            dto.setAdditionalServiceDto(additionalServiceDto);
            additionalServicePriceDtoList.add(dto);
        }
        return new ApiResponse("ok",true,additionalServicePriceDtoList);
    }
}
