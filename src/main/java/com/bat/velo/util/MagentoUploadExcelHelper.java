package com.bat.velo.util;

import com.bat.velo.entity.MagentoUploadEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MagentoUploadExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "fvtest-2";

    public static boolean hasExcelFormat(MultipartFile file) {
        if(!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<MagentoUploadEntity> excelMagentoUpload(InputStream is, String generatedId) {
        try {

            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<MagentoUploadEntity> magentoUploads = new ArrayList<MagentoUploadEntity>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                MagentoUploadEntity mv = new MagentoUploadEntity();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            mv.setIdxNumber((long) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            mv.setSellerReferralCode(currentCell.getStringCellValue());
                            break;

                        case 2:
                            mv.setExternalOrderID(currentCell.getStringCellValue());
                            break;

                        case 3:
                            mv.setOrderStartDate(currentCell.getStringCellValue());
                            break;

                        case 4:
                            mv.setSkuNumber(currentCell.getStringCellValue());
                            break;

                        case 5:
                            mv.setProductName(currentCell.getStringCellValue());
                            break;

                        case 6:
                            mv.setUnitPriceCurrency(currentCell.getStringCellValue());
                            break;

                        case 7:
                            mv.setUnitPrice((long) currentCell.getNumericCellValue());
                            break;

                        case 8:
                            mv.setQuantity((long) currentCell.getNumericCellValue());
                            break;

                        case 9:
                            mv.setOrderAmountCurrency(currentCell.getStringCellValue());
                            break;

                        case 10:
                            mv.setOrderAmount((long) currentCell.getNumericCellValue());
                            break;

                        case 11:
                            mv.setOrderTotalCurrency(currentCell.getStringCellValue());
                            break;

                        case 12:
                            mv.setOrderTotal((long) currentCell.getNumericCellValue());
                            break;

                        default:
                            break;
                    }

                    mv.setGeneratedId(generatedId);

                    cellIdx++;
                }
                magentoUploads.add(mv);
            }
            workbook.close();
            return magentoUploads;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
