package uz.pdp.appg4duonotaryserver.service.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.OutOfService;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.OutOfServiceDto;
import uz.pdp.appg4duonotaryserver.repository.OutOfServiceRepository;
import uz.pdp.appg4duonotaryserver.repository.ZipCodeRepository;
import uz.pdp.appg4duonotaryserver.service.MailService;

import java.util.Optional;

@Service
public class OutOfServiceService {

    @Autowired
    OutOfServiceRepository outOfServiceRepository;

    @Autowired
    MailService mailService;

    @Autowired
    ZipCodeRepository zipCodeRepository;


    public ApiResponse sentEmail(OutOfServiceDto outOfServiceDto) {
        try {
            Optional<OutOfService> byZipCodeIdAndEmail = outOfServiceRepository.findByZipCodeIdAndEmail(outOfServiceDto.getZipCodeId(), outOfServiceDto.getEmail());
            if (!byZipCodeIdAndEmail.isPresent()) {
                ZipCode zipCode = zipCodeRepository.findById(outOfServiceDto.getZipCodeId()).orElseThrow(() -> new ResourceNotFoundException("getZipCode", "getId", outOfServiceDto.getZipCodeId()));
                OutOfService outOfService = new OutOfService();
                outOfService.setEmail(outOfServiceDto.getEmail());
                outOfService.setZipCode(zipCode);
                outOfService.setSent(true);
                outOfServiceRepository.save(outOfService);
                mailService.outOfService(outOfServiceDto.getEmail(), zipCode.getName());
            } else
                return new ApiResponse("Already sended", false);

            return new ApiResponse("sended", true);
        } catch (Exception e) {
            return new ApiResponse("error", false);
        }
    }
}
