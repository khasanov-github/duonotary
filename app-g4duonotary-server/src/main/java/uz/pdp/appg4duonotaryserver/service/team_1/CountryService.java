package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.Country;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CountryDto;
import uz.pdp.appg4duonotaryserver.repository.CountryRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;


import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public ApiResponse saveOrEditCountry(CountryDto countryDto) {
            try {
                Country country = new Country();
                if (countryDto.getId()!=null){
                    country=countryRepository.findById(countryDto.getId()).orElseThrow(() -> new ResourceNotFoundException("getCountry","id",countryDto.getId()));
                }else{
                    if (countryRepository.existsByNameEqualsIgnoreCase(countryDto.getName()))
                        return new ApiResponse("Error", false);
                }
                country.setName(countryDto.getName());
                country.setAbbr(countryDto.getAbbr());
                country.setActive(true);
                country.setEmbassy(countryDto.isEmbassy());
                countryRepository.save(country);
                return new ApiResponse("Country added", true);
            }catch (Exception e){
                return new ApiResponse("Error", false);
            }


    }



    public ApiResponse getCountryPage(int page, int size) {
        try {
            Page<Country> allCountry = countryRepository.findAll(CommonUtils.getPageable(page,size));
            return new ApiResponse("success",true, allCountry.getContent().stream().map(this::getCountryDto).collect(Collectors.toList()),page,allCountry.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }

    private CountryDto getCountryDto(Country country) {
        return new CountryDto(
                country.getId(),
                country.getName(),
                country.getAbbr(),
                country.isActive(),
                country.isEmbassy()
        );

    }

    public ApiResponse getCountry(UUID id) {
        try {
            Country country = countryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getCountry", "id", id));
            return new ApiResponse("success",true,getCountryDto(country));
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }

    }

    public ApiResponse deleteCountry(UUID id) {
        try {
            countryRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }

    }
}
