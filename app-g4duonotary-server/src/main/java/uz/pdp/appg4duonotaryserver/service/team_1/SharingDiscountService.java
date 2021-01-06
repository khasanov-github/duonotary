package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.SharingDiscountGiven;
import uz.pdp.appg4duonotaryserver.entity.SharingDiscountTariff;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.SharingDiscountGivenDto;
import uz.pdp.appg4duonotaryserver.payload.SharingDiscountTariffDto;
import uz.pdp.appg4duonotaryserver.repository.*;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SharingDiscountService {

    @Autowired
    SharingDiscountTariffRepository sharDiscTariffRepozitory;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    ZipCodeRepository zipCodeRepository;

    @Autowired
    SharingDiscountGivenRepository sharDiscGivenRepozitory;

    @Autowired
    ZipCodeService zipCodeService;

    public ApiResponse addOrEditSharingDiscTar(SharingDiscountTariffDto dto) {
        try {
            if (dto.isOnline()) {
                Optional<SharingDiscountTariff> optional = sharDiscTariffRepozitory.findByOnline(true);

                SharingDiscountTariff discountTariff = new SharingDiscountTariff();
                if (optional.isPresent()) {
                    discountTariff = optional.get();
                } else {
                    discountTariff.setOnline(true);
                }
                discountTariff.setActive(dto.isActive());
                discountTariff.setPercent(dto.getPercent());
                sharDiscTariffRepozitory.save(discountTariff);
            } else {
                List<ZipCode> zipCodes = new ArrayList<>();
                if (dto.isAllZipCode()) {
                    zipCodes = zipCodeRepository.findAll();
                } else {
                    if (dto.getZipCodeIds() != null) {
                        zipCodes = zipCodeRepository.findAllById(dto.getZipCodeIds());
                    } else if (dto.getCountyIds() != null) {
                        zipCodes = zipCodeRepository.findAllByCountyIdIn(dto.getCountyIds());
                    } else if (dto.getStateIds() != null) {
                        zipCodes = zipCodeRepository.findAllByCountyStateIdIn(dto.getStateIds());
                    }
                }
                for (ZipCode zipCode : zipCodes) {
                    SharingDiscountTariff sharingDiscountTariff = new SharingDiscountTariff();
                    if (sharDiscTariffRepozitory.existsByZipCode(zipCode)) {
                        sharingDiscountTariff = sharDiscTariffRepozitory.findByZipCode(zipCode);
                    } else {
                        sharingDiscountTariff.setZipCode(zipCode);
                    }
                    sharingDiscountTariff.setPercent(dto.getPercent());
                    sharingDiscountTariff.setActive(dto.isActive());
                    sharDiscTariffRepozitory.save(sharingDiscountTariff);
                }
            }
            return new ApiResponse("ok", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);

        }
    }

//    public ApiResponse getPage(int page, int size) {
//        try {
//            Page<SharingDiscountTariff> all = sharDiscTariffRepozitory.findAll(CommonUtils.getPageable(page, size));
//            return new ApiResponse("success", true, all.getContent().stream().map(this::getDto).collect(Collectors.toList()), page, all.getTotalElements());
//        } catch (IllegalArgumentException e) {
//            return new ApiResponse("Error", false);
//        }
//    }

//    public SharingDiscountTariffDto getDto(SharingDiscountTariff tariff) {
//        return new SharingDiscountTariffDto(
//                tariff.getId(),
//                tariff.isActive(),
//                tariff.isOnline(),
//                tariff.getPercent(),
//                tariff.getZipCode().getId()
//
//        );
//    }

    public ApiResponse getMinMaxList(String search) {
        List<SharingDiscountTariffDto> sharingDiscountTariffDtos = new ArrayList<>();
        SharingDiscountTariffDto dto = new SharingDiscountTariffDto();
        if (search.equals("all")) {
            List<Object[]> minMaxPercentWhereActiveGroupByOnline = sharDiscTariffRepozitory.getMinMaxPercentWhereActiveTrueAndOnlineFalse();
            for (Object[] objects : minMaxPercentWhereActiveGroupByOnline) {
                dto.setMinPercent(Double.parseDouble(objects[0].toString()));
                dto.setMaxPercent(Double.parseDouble(objects[1].toString()));
                dto.setActive(Integer.parseInt(objects[2].toString()) > 0);
            }
            Optional<SharingDiscountTariff> byOnline = sharDiscTariffRepozitory.findByOnline(true);
            if (byOnline.isPresent()) {
                SharingDiscountTariff discountTariff = byOnline.get();
                SharingDiscountTariffDto dtoOnline = new SharingDiscountTariffDto();
                dtoOnline.setId(discountTariff.getId());
                dtoOnline.setActive(discountTariff.isActive());
                dtoOnline.setOnline(discountTariff.isOnline());
                dtoOnline.setPercent(discountTariff.getPercent());
                sharingDiscountTariffDtos.add(dtoOnline);
            }
        } else {
            Optional<SharingDiscountTariff> byZipCodeCode = sharDiscTariffRepozitory.findByZipCodeCode(search);
            if (byZipCodeCode.isPresent()) {
                SharingDiscountTariff discountTariff = byZipCodeCode.get();
                dto.setId(discountTariff.getId());
                dto.setActive(discountTariff.isActive());
                dto.setPercent(discountTariff.getPercent());
                dto.setZipCodeDto(zipCodeService.getZipCodeDto(discountTariff.getZipCode()));
            } else {
                return new ApiResponse("Eror", false);
            }
        }
        sharingDiscountTariffDtos.add(dto);
        return new ApiResponse("OK", true, sharingDiscountTariffDtos);
    }

    public ApiResponse getGivenPage(int page, int size) {
        try {
            Page<SharingDiscountGiven> all = sharDiscGivenRepozitory.findAll(CommonUtils.getPageable(page, size));
            return new ApiResponse("success", true, all.getContent().stream().map(this::getGivenDto).collect(Collectors.toList()), page, all.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }

    public SharingDiscountGivenDto getGivenDto(SharingDiscountGiven sharingDiscountGiven) {
        return new SharingDiscountGivenDto(
                sharingDiscountGiven.getId(),
                sharingDiscountGiven.getClient().getId(),
                sharingDiscountGiven.getPercent(),
                sharingDiscountGiven.getAmount()
        );
    }


//    public ApiResponse getResFirstDiscountTariff(String search) {
//        List<ResFirstOrderDiscountTariff> list = new ArrayList<>();
//        ResFirstOrderDiscountTariff resFirstOrderDiscountTariff = new ResFirstOrderDiscountTariff();
//        if (search.equals("all")) {
//            List<Object[]> objectArr = firstOrderDiscountTariffRepository.getMAxAndMinPercents();
//            for (Object[] objects : objectArr) {
//                resFirstOrderDiscountTariff.setMinPercent(Double.parseDouble(objects[0].toString()));
//                resFirstOrderDiscountTariff.setMaxPercent(Double.parseDouble(objects[1].toString()));
//                resFirstOrderDiscountTariff.setActive(!objects[2].toString().equals("0"));
//            }
//            Optional<FirstOrderDiscountTariff> optionalFirstOrderDiscountTariff = firstOrderDiscountTariffRepository.findByOnline(true);
//            if (optionalFirstOrderDiscountTariff.isPresent()) {
//                FirstOrderDiscountTariff firstOrderDiscountTariff = optionalFirstOrderDiscountTariff.get();
//                ResFirstOrderDiscountTariff resOnline = new ResFirstOrderDiscountTariff();
//                resOnline.setId(firstOrderDiscountTariff.getId());
//                resOnline.setActive(firstOrderDiscountTariff.isActive());
//                resOnline.setOnline(firstOrderDiscountTariff.isOnline());
//                resOnline.setPercent(firstOrderDiscountTariff.getPercent());
//                list.add(resOnline);
//            }
//
//        } else {
//            Optional<FirstOrderDiscountTariff> optionalFirstOrderDiscountTariff = firstOrderDiscountTariffRepository.findByZipCodeCode(search);
//            if (optionalFirstOrderDiscountTariff.isPresent()) {
//                FirstOrderDiscountTariff firstOrderDiscountTariff = optionalFirstOrderDiscountTariff.get();
//                resFirstOrderDiscountTariff.setId(firstOrderDiscountTariff.getId());
//                resFirstOrderDiscountTariff.setActive(firstOrderDiscountTariff.isActive());
//                resFirstOrderDiscountTariff.setPercent(firstOrderDiscountTariff.getPercent());
//                resFirstOrderDiscountTariff.setZipCodeDto(zipCodeService.getZipCode(firstOrderDiscountTariff.getZipCode()));
//            } else {
//                return new ApiResponse("Error", false);
//            }
//        }
//        list.add(resFirstOrderDiscountTariff);
//        return new ApiResponse("Ok", true, list);
//    }
}
