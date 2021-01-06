package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.County;
import uz.pdp.appg4duonotaryserver.entity.CustomDiscountTariff;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CustomDiscountTariffDtoAsomiddin;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.repository.CustomDiscountTariffRepository;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.stream.Collectors;

@Service
public class CustomDiscountTariffServiceAsomiddin {
    @Autowired
    CustomDiscountTariffRepository customDiscountTariffRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse saveOrEditCustomDisvountTariff(CustomDiscountTariffDtoAsomiddin customDiscountTariffDtoAsomiddin) {
        try {
            CustomDiscountTariff customDiscountTariff = new CustomDiscountTariff();
            if (customDiscountTariff.getId() != null) {
                customDiscountTariff = customDiscountTariffRepository.findById(customDiscountTariff.getId()).orElseThrow(() -> new ResourceNotFoundException("getCustomDiscountTariffId", "Id", customDiscountTariffDtoAsomiddin.getId()));
                if (!customDiscountTariffDtoAsomiddin.isUnlimited()) {
                    if (customDiscountTariff.getUsedCount() <= customDiscountTariffDtoAsomiddin.getGivenCount()) {
                        customDiscountTariff.setGivenCount(customDiscountTariffDtoAsomiddin.getGivenCount());
                    } else {
                        return new ApiResponse("Invalid given count", false);
                    }
                } else {
                    customDiscountTariff.setGivenCount(null);
                }

            } else {
                if (!customDiscountTariffDtoAsomiddin.isUnlimited()) {
                    customDiscountTariff.setGivenCount(customDiscountTariffDtoAsomiddin.getGivenCount());
                }
                customDiscountTariff.setClient(userRepository.findById(customDiscountTariffDtoAsomiddin.getClientId()).orElseThrow(() -> new ResourceNotFoundException("getClientId", "id", customDiscountTariffDtoAsomiddin.getClientId())));
            }
            customDiscountTariff.setPercent(customDiscountTariffDtoAsomiddin.getPercent());
            customDiscountTariff.setUnlimited(customDiscountTariffDtoAsomiddin.isUnlimited());
            customDiscountTariff.setActive(customDiscountTariffDtoAsomiddin.isActive());
            customDiscountTariffRepository.save(customDiscountTariff);
            return new ApiResponse(customDiscountTariffDtoAsomiddin.getId() != null ? "Edit" : "Save", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public CustomDiscountTariffDtoAsomiddin customDiscountTariffDtoAsomiddin(CustomDiscountTariff customDiscountTariff) {
        return new CustomDiscountTariffDtoAsomiddin(
                customDiscountTariff.getId(),
                customDiscountTariff.getClient().getId(),
                customDiscountTariff.getPercent().doubleValue(),
                customDiscountTariff.isActive(),
                customDiscountTariff.isUnlimited(),
                customDiscountTariff.getGivenCount(),
                customDiscountTariff.getUsedCount(),
                new UserDto(
                        customDiscountTariff.getClient().getFirstName(),
                        customDiscountTariff.getClient().getLastName(),
                        customDiscountTariff.getClient().getPhoneNumber(),
                        customDiscountTariff.getClient().getEmail()
                )
        );

    }

    public ApiResponse getCustomDiscountTariffList(int page, int size) {
        Page<CustomDiscountTariff> customDiscountTariffPage = customDiscountTariffRepository.findAll(CommonUtils.getPageable(page, size));
        return new ApiResponse("Ok", true, customDiscountTariffPage.getContent().stream().map(this::customDiscountTariffDtoAsomiddin).collect(Collectors.toList()), page, customDiscountTariffPage.getTotalElements());
    }
}

