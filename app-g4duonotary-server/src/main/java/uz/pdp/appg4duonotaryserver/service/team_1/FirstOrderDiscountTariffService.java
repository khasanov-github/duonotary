package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.FirstOrderDiscountGiven;
import uz.pdp.appg4duonotaryserver.entity.FirstOrderDiscountTariff;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.FirstOrderDiscountGivenDto;
import uz.pdp.appg4duonotaryserver.payload.FirstOrderDiscountTariffDto;
import uz.pdp.appg4duonotaryserver.repository.FirstOrderDiscountGivenRepository;
import uz.pdp.appg4duonotaryserver.repository.FirstOrderDiscountTariffRepository;
import uz.pdp.appg4duonotaryserver.repository.ZipCodeRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FirstOrderDiscountTariffService {

    @Autowired
    FirstOrderDiscountTariffRepository firstOrderDiscountTariffRepository;
    @Autowired
    FirstOrderDiscountGivenRepository firstOrderDiscountGivenRepository;
    @Autowired
    ZipCodeRepository zipCodeRepository;
    @Autowired
    ZipCodeService zipCodeService;

    public ApiResponse saveFirstOrderDiscountTariff(FirstOrderDiscountTariffDto dto) {

        try {
            if (dto.isOnline()) {
                Optional<FirstOrderDiscountTariff> byOnline = firstOrderDiscountTariffRepository.findByOnline(true);
                FirstOrderDiscountTariff firstOrderDiscountTariff = new FirstOrderDiscountTariff();

                if (byOnline.isPresent()) {
                    firstOrderDiscountTariff = byOnline.get();
                }
                firstOrderDiscountTariff.setOnline(dto.isOnline());
                firstOrderDiscountTariff.setPercent(dto.getPercent());
                firstOrderDiscountTariff.setActive(dto.isActive());
                firstOrderDiscountTariffRepository.save(firstOrderDiscountTariff);

            } else {
                List<ZipCode> zipCodes = new ArrayList<>();
                if (dto.isAllZipCodes()) {
                    zipCodes = zipCodeRepository.findAll();
                } else {
                    if (dto.getZipCodes() != null) {
                        zipCodes = zipCodeRepository.findAllById(dto.getZipCodes());
                    } else if (dto.getCounties() != null) {
                        zipCodes = zipCodeRepository.findAllByCountyIdIn(dto.getCounties());
                    } else if (dto.getStates() != null) {
                        zipCodes = zipCodeRepository.findAllByCountyStateIdIn(dto.getStates());
                    }
                }

                for (ZipCode zipCode : zipCodes) {
                    FirstOrderDiscountTariff firstOrderDiscountTariff = new FirstOrderDiscountTariff();
                    if (firstOrderDiscountTariffRepository.existsByZipCode(zipCode)) {
                        firstOrderDiscountTariff = firstOrderDiscountTariffRepository.findByZipCode(zipCode);
                    } else {
                        firstOrderDiscountTariff.setZipCode(zipCode);
                    }
                    firstOrderDiscountTariff.setPercent(dto.getPercent());
                    firstOrderDiscountTariff.setActive(dto.isActive());
                    firstOrderDiscountTariff.setOnline(false);
                    firstOrderDiscountTariffRepository.save(firstOrderDiscountTariff);
                }
            }
            return new ApiResponse("Ok", true);
        } catch (Exception e) {
            return new ApiResponse("error", false);
        }
    }



    public ApiResponse getMinMaxPercentFirstOrderDiscountTariff(String search) {
        List<FirstOrderDiscountTariffDto> tariffDtoList = new ArrayList<>();
        FirstOrderDiscountTariffDto firstOrderDiscountTariffDto = new FirstOrderDiscountTariffDto();
        if (search.equals("all")) {
            List<Object[]> objectArr = firstOrderDiscountTariffRepository.getMinMaxPercentDiscountFirstOrderDiscountTariffAndActive();
            for (Object[] objects : objectArr) {
                firstOrderDiscountTariffDto.setMinPercent(Double.parseDouble(objects[0].toString()));
                firstOrderDiscountTariffDto.setMaxPercent(Double.parseDouble(objects[1].toString()));
                firstOrderDiscountTariffDto.setActive(Integer.parseInt(objects[2].toString()) > 0);
            }
            Optional<FirstOrderDiscountTariff> optional = firstOrderDiscountTariffRepository.findByOnline(true);
            if(optional.isPresent()){
                FirstOrderDiscountTariff firstOrderDiscountTariff = optional.get();
                FirstOrderDiscountTariffDto firstOrderDiscountTariffDtoOnline=new FirstOrderDiscountTariffDto();
                firstOrderDiscountTariffDtoOnline.setId(firstOrderDiscountTariff.getId());
                firstOrderDiscountTariffDtoOnline.setActive(firstOrderDiscountTariff.isActive());
                firstOrderDiscountTariffDtoOnline.setOnline(firstOrderDiscountTariff.isOnline());
                firstOrderDiscountTariffDtoOnline.setPercent(firstOrderDiscountTariff.getPercent());
                tariffDtoList.add(firstOrderDiscountTariffDtoOnline);
            }
        }else{
            Optional<FirstOrderDiscountTariff> optional =
                    firstOrderDiscountTariffRepository.findByZipCodeCode(search);
            if(optional.isPresent()){
                FirstOrderDiscountTariff firstOrderDiscountTariff = optional.get();
                firstOrderDiscountTariffDto.setId(firstOrderDiscountTariff.getId());
                firstOrderDiscountTariffDto.setActive(firstOrderDiscountTariff.isActive());
                firstOrderDiscountTariffDto.setPercent(firstOrderDiscountTariff.getPercent());
                firstOrderDiscountTariffDto.setZipCodeDto(zipCodeService.getZipCodeDto(firstOrderDiscountTariff.getZipCode()));
            }else {
                return new ApiResponse("Error", false);
            }
        }
        tariffDtoList.add(firstOrderDiscountTariffDto);
        return new ApiResponse("OK", true, tariffDtoList);
    }


    public ApiResponse getFirstOrderDiscountGivenList(int page, int size) {
        try {
            Page<FirstOrderDiscountGiven> firstOrderDiscountGivenPage =
                    firstOrderDiscountGivenRepository.findAll(CommonUtils.getPageable(page, size));

            return new ApiResponse("Ok", true, firstOrderDiscountGivenPage
                    .getContent()
                    .stream()
                    .map(this::getFirstOrderDiscountGivenDto)
                    .collect(Collectors.toList()), page, firstOrderDiscountGivenPage.getTotalElements());

        } catch (IllegalArgumentException e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public FirstOrderDiscountGivenDto getFirstOrderDiscountGivenDto
            (FirstOrderDiscountGiven firstOrderDiscountGiven) {
        return new FirstOrderDiscountGivenDto(
                firstOrderDiscountGiven.getId(),
                firstOrderDiscountGiven.getClient().getId(),
                firstOrderDiscountGiven.getClient().getFirstName(),
                firstOrderDiscountGiven.getClient().getLastName(),
                firstOrderDiscountGiven.getPercent(),
                firstOrderDiscountGiven.getAmount()
        );
    }

}
