package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.LoyaltyDiscountGiven;
import uz.pdp.appg4duonotaryserver.entity.LoyaltyDiscountTariff;
import uz.pdp.appg4duonotaryserver.entity.Order;
import uz.pdp.appg4duonotaryserver.entity.enums.OrderStatus;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.LoyaltyDiscountGivenDto;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.repository.LoyaltyDiscountGivenRepository;
import uz.pdp.appg4duonotaryserver.repository.LoyaltyDiscountTariffRepository;
import uz.pdp.appg4duonotaryserver.repository.OrderRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoyaltyDiscountGivenService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    LoyaltyDiscountTariffRepository loyaltyDiscountTariffRepository;

    @Autowired
    LoyaltyDiscountGivenRepository loyaltyDiscountGivenRepository;


    public ApiResponse addLoyaltyDiscountTariffs(Order order) {
        try {
            LoyaltyDiscountTariff tariff = loyaltyDiscountTariffRepository.findAllByActiveTrue().
                    orElseThrow(() -> new ResourceNotFoundException("getLoyaltyDiscountTariff"));
            Optional<LoyaltyDiscountGiven> allByClient = loyaltyDiscountGivenRepository.findAllByClient(order.getClient());
            if (tariff.isActive()) {
                if (order.getAmount() >= tariff.getMinAmount()) {
                    if (allByClient.isPresent()) {
                        LoyaltyDiscountGiven loyaltyDiscountGiven = allByClient.get();
                        if (order.getOrderStatus().equals("COMPLETED")) {
                            Double amount = (order.getAmount() / 100) * tariff.getPercent();
                            loyaltyDiscountGiven.setAmount(amount);
                            loyaltyDiscountGiven.setPercent(tariff.getPercent());
                            loyaltyDiscountGiven.setTotalSum(amount);
                            loyaltyDiscountGivenRepository.save(loyaltyDiscountGiven);
                        }
                    }
                }
            }
            return new ApiResponse("success", true);
        } catch (Exception e) {
            return null;
        }

    }


    public ApiResponse addLoyaltyDiscountTariff(Order order) {
        try {
            LoyaltyDiscountTariff tariff = loyaltyDiscountTariffRepository.findAllByActiveTrue().
                    orElseThrow(() -> new ResourceNotFoundException("getLoyaltyDiscountTariff"));
            List<Order> atMonth = orderRepository.findAllByClientIdAndOrderStatus(
                    order.getClient().getId(), OrderStatus.COMPLETED);
            Double amount = 0.0;
            if (tariff.isActive()) {
                for (Order order1 : atMonth) {
                    amount += order1.getAmount();
                }
                if (amount >= tariff.getMinAmount())
                    amount = (amount / 100) * tariff.getPercent();
                LoyaltyDiscountGiven loyaltyDiscountGiven = new LoyaltyDiscountGiven();
                loyaltyDiscountGiven.setAmount(amount);
                loyaltyDiscountGiven.setPercent(tariff.getPercent());
                loyaltyDiscountGiven.setTotalSum(amount);
                loyaltyDiscountGivenRepository.save(loyaltyDiscountGiven);
            } else {
                return new ApiResponse(" tariff false ", false);
            }
            return new ApiResponse("Success", true);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Errors", false);
        }
    }


    public ApiResponse getLoyaltyDiscountGiven(Integer page, Integer size) {
        try {
            Page<LoyaltyDiscountGiven> allOrderByCreatedAtDesc = loyaltyDiscountGivenRepository.findAllOrderByCreatedAtDesc(CommonUtils.getPageable(page, size));
            List<LoyaltyDiscountGivenDto> loyaltyDiscountGivenDtoList = new ArrayList<>();
            for (LoyaltyDiscountGiven loyaltyDiscountGiven : allOrderByCreatedAtDesc.getContent()) {
                LoyaltyDiscountGivenDto loyaltyDiscountGivenDto = new LoyaltyDiscountGivenDto();
                loyaltyDiscountGivenDto.setClient(new UserDto(loyaltyDiscountGiven.getClient().getId(),
                        loyaltyDiscountGiven.getClient().getFirstName(),
                        loyaltyDiscountGiven.getClient().getLastName(),
                        loyaltyDiscountGiven.getClient().getPhoneNumber(),
                        loyaltyDiscountGiven.getClient().getEmail()));
                loyaltyDiscountGivenDto.setPercent(loyaltyDiscountGiven.getPercent());
                loyaltyDiscountGivenDto.setAmount(loyaltyDiscountGiven.getAmount());
                loyaltyDiscountGivenDto.setTotalSum(loyaltyDiscountGiven.getTotalSum());
                loyaltyDiscountGivenDtoList.add(loyaltyDiscountGivenDto);
            }
            return new ApiResponse("success", true, loyaltyDiscountGivenDtoList, page, allOrderByCreatedAtDesc.getTotalElements());
        } catch (Exception e) {
            return new ApiResponse("Errors", false);
        }

    }
}


