package uz.pdp.appg4duonotaryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.State;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.StateDto;
import uz.pdp.appg4duonotaryserver.repository.StateRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StateService {
    @Autowired
    StateRepository stateRepository;

    public ApiResponse saveOrEditState(StateDto stateDto) {
        try {
            State state = new State();
            if (stateDto.getId() != null) {
                state = stateRepository.findById(stateDto.getId()).orElseThrow(() -> new ResourceNotFoundException("getStateId", "id", stateDto.getId()));
            }
            state.setName(stateDto.getName());
            if (stateDto.getDescription() != null) {
                state.setDescription(stateDto.getDescription());
            }
            stateRepository.save(state);
            return new ApiResponse("State add", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public StateDto stateDto(State state) {
        return new StateDto(
                state.getId(), state.getName()
        );
    }

    public ApiResponse getStateId(UUID id) {
        try {
            State state = stateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getState", "id", id));
            return new ApiResponse("Ok", true, stateDto(state));
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse getStatePage(int page, int size) {
        Page<State> statePage = stateRepository.findAll(CommonUtils.getPageable(page, size));
        return new ApiResponse("Ok", true, statePage.getContent().stream().map(this::stateDto).collect(Collectors.toList()), page, statePage.getTotalElements());
    }

    public ApiResponse deleteState(UUID id) {
        try {
            stateRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }
}
