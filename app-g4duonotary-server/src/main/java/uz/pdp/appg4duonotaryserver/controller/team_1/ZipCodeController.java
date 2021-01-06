package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.ZipCodeDto;
import uz.pdp.appg4duonotaryserver.repository.ZipCodeRepository;
import uz.pdp.appg4duonotaryserver.service.team_1.ZipCodeService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/zipCode")
public class ZipCodeController {
    @Autowired
    ZipCodeService zipCodeService;


    @Autowired
    ZipCodeRepository zipCodeRepository;

    @PostMapping
    public HttpEntity<?> addZipCode(@RequestBody ZipCodeDto zipCodeDto) {
        ApiResponse apiResponse = zipCodeService.saveOrEditZipCode(zipCodeDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved")? 201 : 202 : 409).body(apiResponse);
    }


    @GetMapping("/getPage")
    public HttpEntity<?> getAll(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        ApiResponse allZipCode = zipCodeService.getAllZipCode(page, size);
        return ResponseEntity.status(allZipCode.isSuccess()?200:409).body(allZipCode);
    }


    @DeleteMapping("/{zipCodeId}")
    public HttpEntity<?> deleteZipCode(@PathVariable UUID zipCodeId ){
        ApiResponse apiResponse = zipCodeService.deletedZipCode(zipCodeId);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }










//            Excel file ga save qilish
    @GetMapping("/download/customers.xlsx")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");
        List<ZipCode> all = zipCodeRepository.findAll();
        ByteArrayInputStream stream = zipCodeService.contactListToExcelFile(all);
        IOUtils.copy(stream, response.getOutputStream());
    }




}
