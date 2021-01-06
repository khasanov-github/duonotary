package uz.pdp.appg4duonotaryserver.service.team_2;

import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.*;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.AdditionalServicePriceDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.OrderDto;
import uz.pdp.appg4duonotaryserver.repository.*;

import java.util.*;

@Service
public class OrderService {
    final
    OrderRepository orderRepository;

    final
    ZipCodeRepository zipCodeRepository;
    final
    ServicePriceRepository servicePriceRepository;
    final
    ServiceRepository serviceRepository;
    final
    AdditionalServiceRepository additionalServiceRepository;
    final
    AdditionalServicePriceRepository additionalServicePriceRepository;
    final
    PricingRepository pricingRepository;

    public OrderService(OrderRepository orderRepository, ZipCodeRepository zipCodeRepository,
                        ServicePriceRepository servicePriceRepository, ServiceRepository serviceRepository, AdditionalServiceRepository additionalServiceRepository, AdditionalServicePriceRepository additionalServicePriceRepository, PricingRepository pricingRepository) {
        this.orderRepository = orderRepository;
        this.zipCodeRepository = zipCodeRepository;
        this.servicePriceRepository = servicePriceRepository;
        this.serviceRepository = serviceRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.additionalServicePriceRepository = additionalServicePriceRepository;
        this.pricingRepository = pricingRepository;
    }

    public ApiResponse chooseServicePrice(UUID servicePriceId) {
        Optional<ServicePrice> optional = servicePriceRepository.findById(servicePriceId);
        if (optional.isPresent()) {
            ServicePrice servicePrice = optional.get();
            ZipCode zipCode = servicePrice.getZipCode();
            uz.pdp.appg4duonotaryserver.entity.Service service = servicePrice.getService();
            List<AdditionalServicePriceDto> list = new ArrayList<>();
            List<AdditionalService> allByServices = additionalServiceRepository.findAllByServices(Collections.singletonList(service));
            for (AdditionalService additionalService : allByServices) {
                Optional<AdditionalServicePrice> byZipCodeAndAdditionalService =
                        additionalServicePriceRepository.findByZipCodeAndAdditionalServiceAndActiveTrue(zipCode, additionalService);
                if (byZipCodeAndAdditionalService.isPresent()) {
                    AdditionalServicePrice additionalServicePrice = byZipCodeAndAdditionalService.get();
                    list.add(
                            new AdditionalServicePriceDto(additionalServicePrice.getId(), additionalService.getName(), additionalServicePrice.getPrice()));
                }
            }
            return new ApiResponse("Ok", true, list);
        }
        return new ApiResponse("Error", false);
    }




    public ApiResponse getPriceByCount(UUID serPriceId, Integer count) {
        Optional<ServicePrice> byId = servicePriceRepository.findById(serPriceId);
        if (byId.isPresent()) {
            ServicePrice servicePrice = byId.get();
            Double price = servicePrice.getPrice();
            List<Pricing> all = pricingRepository.findAllByServicePriceIdAndActiveTrue(serPriceId);
            for (Pricing pricing : all) {
                if (pricing.getTillCount() != null) {
                    if (count >= pricing.getFromCount() && count <= pricing.getTillCount()) {
                        price+=pricing.getPrice()*count;
                    }
                } else {
                    if (count >= pricing.getFromCount()) {
                        price+=pricing.getPrice()*count;
                    }
                }
            }
            return new ApiResponse("ok",true,price);
        }
        return new ApiResponse("error",false);
    }

    public ApiResponse getOne(UUID id){
     Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "getOrder",id));
     return new  ApiResponse("ok",true, makeOrderDto(order));
     }

    public OrderDto makeOrderDto(Order order){
        return new OrderDto(order.getAddress(),
                order.getClient().getId(),
                order.getLan(),
                order.getLat(),
                order.getServicePrice().getId(),
                order.getAgent().getId(),
                order.getAmount(),
                order.getAmountDiscount(),
                order.getCheckNumber(),
                order.getSerialNumber(),
                order.getOrderStatus(),
                order.getCountDocument(),
                order.getDocuments(),
                order.getDocVerifyDocuments(),
                order.getDocVerifyId(),
                order.isPacket()
                );
    }

    public Integer getCountAgentOrders(UUID agentId){
        List<Order> all = orderRepository.findAllByAgentId(agentId);
        return all.size();
    }


}