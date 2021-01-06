package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.County;
import uz.pdp.appg4duonotaryserver.entity.State;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CountyDtoAsomiddin;
import uz.pdp.appg4duonotaryserver.payload.StateDto;
import uz.pdp.appg4duonotaryserver.repository.CountyRepository;
import uz.pdp.appg4duonotaryserver.repository.StateRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CountyServiceAsomiddin {
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    StateRepository stateRepository;

    public ApiResponse saveOrEditCounty(CountyDtoAsomiddin countyDtoAsomiddin) {
        try {
            County county = new County();
            if (county.getId() != null) {
                county = countyRepository.findById(county.getId()).orElseThrow(() -> new ResourceNotFoundException("getCountyId", "Id", countyDtoAsomiddin.getId()));

            } else {
                county.setName(countyDtoAsomiddin.getName());
                State state = stateRepository.findById(countyDtoAsomiddin.getStateId()).orElseThrow(() -> new ResourceNotFoundException("getStateId", "Id", countyDtoAsomiddin.getStateId()));
                county.setState(state);
                countyRepository.save(county);
            }
            return new ApiResponse(countyDtoAsomiddin.getId() != null ? "Edit" : "Save", true);
            } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse getCountyId(UUID id) {
        try {
            County county = countyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getCounty", "id", id));
            return new ApiResponse("Ok", true, countyDtoAsomiddin(county));
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public CountyDtoAsomiddin countyDtoAsomiddin(County county) {
        return new CountyDtoAsomiddin(
                county.getId(), county.getName(), county.getState().getId()
        );
    }

    public ApiResponse getCountyList(int page, int size) {
        Page<County> countyPage = countyRepository.findAll(CommonUtils.getPageable(page, size));
        return new ApiResponse("Ok", true, countyPage.getContent().stream().map(this::countyDtoAsomiddin).collect(Collectors.toList()), page, countyPage.getTotalElements());
    }

    public ApiResponse deleteCountyAsomiddin(UUID id) {
        try {
            countyRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }
    }
}