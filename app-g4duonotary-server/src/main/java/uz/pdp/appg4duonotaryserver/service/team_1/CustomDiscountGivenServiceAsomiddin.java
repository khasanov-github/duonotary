package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import uz.pdp.appg4duonotaryserver.entity.County;
import uz.pdp.appg4duonotaryserver.entity.CustomDiscountGiven;
import uz.pdp.appg4duonotaryserver.entity.CustomDiscountTariff;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CountyDtoAsomiddin;
import uz.pdp.appg4duonotaryserver.payload.CustomDiscountGivenDtoAsomiddin;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.repository.CustomDiscountGivenRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.stream.Collectors;

@Service
public class CustomDiscountGivenServiceAsomiddin {
    @Autowired
    CustomDiscountGivenRepository customDiscountGivenRepository;

    public CustomDiscountGivenDtoAsomiddin customDiscountGivenDtoAsomiddin(CustomDiscountGiven customDiscountGiven) {
        return new CustomDiscountGivenDtoAsomiddin(
                customDiscountGiven.getPercent(),
                new UserDto(customDiscountGiven.getClient().getFirstName(),
                        customDiscountGiven.getClient().getLastName(),
                        customDiscountGiven.getClient().getPhoneNumber(),
                        customDiscountGiven.getClient().getEmail()),
                customDiscountGiven.getAmount(),
                customDiscountGiven.isFromTariff()
        );
    }

    public ApiResponse getCustomDiscountGivenList(int page, int size) {
        Page<CustomDiscountGiven> customDiscountGivenPage = customDiscountGivenRepository.findAll(CommonUtils.getPageable(page, size));
        return new ApiResponse("Ok", true, customDiscountGivenPage.getContent().stream().map(this::customDiscountGivenDtoAsomiddin).collect(Collectors.toList()), page, customDiscountGivenPage.getTotalElements());
    }
}
