package uz.pdp.appg4duonotaryserver.utils;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import uz.pdp.appg4duonotaryserver.exceptions.BadRequestException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CertificateDto;

import java.util.Date;

public class CommonUtils {

    public static ApiResponse agentCertificateOrPassportExpired(CertificateDto certificateDto) {
        return new ApiResponse((certificateDto.getStateId() == null ? "Id" : "Certificate ") + " expired", certificateDto.getExpireDate().before(new Date()));
    }

    public static ApiResponse agentCertificateOrPassportNotValidDate(CertificateDto certificateDto) {
        return new ApiResponse((certificateDto.getStateId() == null ? "Id" : "Certificate ") + " expired", certificateDto.getIssueDate().after(new Date()));
    }
    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Sahifa soni noldan kam bo'lishi mumkin emas.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE * 10) {
            throw new BadRequestException("Sahifa soni " + AppConstants.MAX_PAGE_SIZE + " dan ko'p bo'lishi mumkin emas.");
        }
    }

    public static Pageable getPageable(int page, int size) {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size);
    }



}
