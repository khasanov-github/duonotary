package uz.pdp.appg4duonotaryserver.service.team_1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.State;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.*;
import uz.pdp.appg4duonotaryserver.repository.CountyRepository;
import uz.pdp.appg4duonotaryserver.repository.StateRepository;
import uz.pdp.appg4duonotaryserver.repository.ZipCodeRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ZipCodeService {
    @Autowired
    ZipCodeRepository zipCodeRepository;
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    StateRepository stateRepository;


    public ApiResponse saveOrEditZipCode(ZipCodeDto zipCodeDto) {
        try {
            if (zipCodeDto.getId() != null) {
                Optional<ZipCode> optionalZipCode = zipCodeRepository.findById(zipCodeDto.getId());
                if (optionalZipCode.isPresent()) {
                    ZipCode zipCode1 = optionalZipCode.get();
                    if (!zipCode1.getCode().equals(zipCodeDto.getCode())) {
                        zipCode1.setCode(zipCodeDto.getCode());
                    }
                    zipCode1.setActive(zipCodeDto.isActive());
                    zipCode1.setName(zipCodeDto.getName());
                    zipCode1.setCounty(countyRepository.findById(
                            zipCodeDto.getCountyId())
                            .orElseThrow(() -> new ResourceNotFoundException
                                    ("getCounty", "Id", zipCodeDto.getCountyId())));
                    zipCodeRepository.save(zipCode1);

                } else {
                    return new ApiResponse("yoq", false);
                }
            } else {
                ZipCode zipCode = new ZipCode();
                zipCode.setCode(zipCodeDto.getCode());
                zipCode.setActive(zipCodeDto.isActive());
                zipCode.setName(zipCodeDto.getName());
                zipCode.setCounty(countyRepository.findById(
                        zipCodeDto.getCountyId())
                        .orElseThrow(() -> new ResourceNotFoundException
                                ("getCounty", "Id", zipCodeDto.getCountyId())));
                zipCodeRepository.save(zipCode);
            }
            return new ApiResponse(zipCodeDto.getId() != null ? "Edited" : "Saved", true);
        } catch (Exception e) {
            return new ApiResponse("Error ", false);
        }
    }

    public ApiResponse getAllZipCode(int page, int size) {
        try {
            List<ZipCodeStateDto> zipCodeStateDtoList=new ArrayList<>();
            Page<String> stateIds = zipCodeRepository.getStateIds(CommonUtils.getPageable(page, size));
            for (String stateId : stateIds.getContent()) {
                State state = stateRepository.findById(UUID.fromString(stateId)).orElseThrow(() -> new ResourceNotFoundException("", "", UUID.fromString(stateId)));
                ZipCodeStateDto stateDto=new ZipCodeStateDto();
                stateDto.setStateName(state.getName());
                List<ZipCodeDto> zipCodeDtoList=new ArrayList<>();
                List<Object[]> zcCodeAndId = zipCodeRepository.getZCodeAndId(state.getId());
                for (Object[] objects : zcCodeAndId) {
                    ZipCodeDto zipCodeDto=new ZipCodeDto();
                    zipCodeDto.setId(UUID.fromString(objects[0].toString()));
                    zipCodeDto.setCode(objects[1].toString());
                    zipCodeDtoList.add(zipCodeDto);
                }
                stateDto.setZipCodes(zipCodeDtoList);
            }
            return new ApiResponse("succes", true,
                    zipCodeStateDtoList, page, stateIds.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse(" Error", false);

        }
    }


    public ApiResponse deletedZipCode(UUID zipCodeId) {
        try {
            Optional<ZipCode> byId = zipCodeRepository.findById(zipCodeId);
            if (byId.isPresent()){
                byId.get().setActive(!byId.get().isActive());
                zipCodeRepository.save(byId.orElseThrow(() -> new ResourceNotFoundException(" "," ", byId.get())));
            }
            return new ApiResponse("delete",true);
        }catch (Exception e) {
            return new ApiResponse("Error ",false);
        }
    }


    public ZipCodeDto getZipCodeDto(ZipCode zipCode) {
        return new ZipCodeDto(
                zipCode.getId(),
                zipCode.getCode(),
                zipCode.getName(),
                new CountyDto(zipCode.getId(),
                        zipCode.getCounty().getName(),
                        new StateDto(zipCode.getCounty().getState().getId(),
                                zipCode.getCounty().getState().getName())),
                zipCode.isActive()
        );
    }










//Excel file ga save qilish

    public ByteArrayInputStream contactListToExcelFile(List<ZipCode> zipCode) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("ZipCode");
//            List<ZipCode> zipCodeList = new ArrayList<>();
            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("code");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Mobile");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Email");
            cell.setCellStyle(headerCellStyle);


            // Creating data rows for each customer
            for (int i = 0; i < zipCode.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(zipCode.get(i).getName());
                dataRow.createCell(1).setCellValue(zipCode.get(i).getCode());
                dataRow.createCell(2).setCellValue(String.valueOf(zipCode.get(i).getCounty().getId()));
                dataRow.createCell(3).setCellValue(zipCode.get(i).isActive());
            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
