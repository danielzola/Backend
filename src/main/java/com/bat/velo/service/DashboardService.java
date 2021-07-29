package com.bat.velo.service;

import com.bat.velo.dto.DashboardDataDTO;
import com.bat.velo.dto.DashboardSerieDTO;
import com.bat.velo.entity.DashboardItem;
import com.bat.velo.repository.DashboardItemRepository;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    
    @Autowired
    protected DashboardItemRepository itemRepo;
    
    @Autowired
    private EntityManager entityManager;
    
    public byte[] getXLS(String name, Map<String, Object> params) throws Exception {
    
        byte[] result = null;
        
        DashboardItem item = itemRepo.findByName(name);
        
        if (item != null) {
            
            Query query = entityManager.createNativeQuery(item.getSqlQuery(), Tuple.class);
        
            if (params != null) {

                for (String key : params.keySet()) {

                    if (key.equalsIgnoreCase("pageNum")) {

                        int pageSize = 10;
                        int pageNum = 1;

                        try {
                            pageNum = Integer.valueOf(String.valueOf(params.get("pageNum")));
                            pageSize = Integer.valueOf(String.valueOf(params.get("pageSize")));
                        }
                        catch (Exception e) {

                        }

                        query.setParameter(key, ((pageNum - 1) * pageSize));
                    }
                    else
                        query.setParameter(key, params.get(key));
                }
            }

            List<Tuple> ret = query.getResultList();

            boolean first = true;
            
            List<String> headerData = new ArrayList<>();
            List<List<Object>> data = new ArrayList<>();
            
            for (Tuple row: ret){

                List<TupleElement<?>> elements = row.getElements();

                if (first) {

                    for (TupleElement<?> element : elements ) {

                        headerData.add(element.getAlias());
                    }

                    first = false;
                }
                
                data.add(Arrays.asList(row.toArray()));
            }
            
            Workbook workbook = new XSSFWorkbook();
 
            Sheet sheet = workbook.createSheet(name);
            Row header = sheet.createRow(0);

            int colNo = 0;
            int rowNo = 1;

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);

            for (String txt : headerData) {

                Cell headerCell = header.createCell(colNo);
                headerCell.setCellValue(txt);
                headerCell.setCellStyle(headerStyle);

                colNo++;
            }

            for (List<Object> row : data) {

                Row dataRow = sheet.createRow(rowNo);
                rowNo++;
                colNo = 0;

                for (Object obj : row) {

                    Cell cell = dataRow.createCell(colNo);

                    if (obj == null)
                        cell.setCellValue("");
                    else if (obj instanceof LocalDate)
                        cell.setCellValue(Date.from(((LocalDate) obj).atStartOfDay().toInstant(ZoneOffset.UTC)));
                    else if ((obj instanceof Double) || (obj instanceof Long) || (obj instanceof Integer))
                        cell.setCellValue(Double.parseDouble(String.valueOf(obj)));
                    else 
                        cell.setCellValue(String.valueOf(obj));

                    cell.setCellStyle(dataStyle);

                    colNo++;
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                workbook.write(bos);
            } 
            finally {
                bos.close();
                workbook.close();
            }

            result = bos.toByteArray();
        }
        else
            throw new Exception("Dashboard item is not found");
    
        return result;
    }
    
    public List getDtoList(String name, Map<String, Object> params) throws Exception {
        
        List result = new ArrayList();
        
        DashboardItem item = itemRepo.findByName(name);
        
        if (item != null) {
        
            if (item.getDataType().equalsIgnoreCase("DTO")) {
            
                SQLQuery query = entityManager.createNativeQuery(item.getSqlQuery()).unwrap(NativeQuery.class);
        
                if (params != null) {

                    for (String key : params.keySet()) {
                        
                        if (key.equalsIgnoreCase("pageNum")) {
                            
                            int pageSize = 10;
                            int pageNum = 1;
                            
                            try {
                                pageNum = Integer.valueOf(String.valueOf(params.get("pageNum")));
                                pageSize = Integer.valueOf(String.valueOf(params.get("pageSize")));
                            }
                            catch (Exception e) {
                                
                            }
                            
                            query.setParameter(key, ((pageNum - 1) * pageSize));
                        }
                        else
                            query.setParameter(key, params.get(key));
                    }
                }
        
                query.setResultTransformer(Transformers.aliasToBean(Class.forName(item.getTargetClass())));

                result = query.getResultList();
            }
        }
        else
            throw new Exception("Dashboard item is not found");
        
        return result;
    }
    
    public DashboardDataDTO getData(String name, Map<String, Object> params) throws Exception {
        
        DashboardDataDTO result = new DashboardDataDTO();
        
        DashboardItem item = itemRepo.findByName(name);
        
        if (item != null) {
        
            result.setType(item.getDataType());
            
            if (item.getDataType().equalsIgnoreCase("OneValue")) {
            
                Query query = entityManager.createNativeQuery(item.getSqlQuery());
        
                if (params != null) {

                    for (String key : params.keySet()) {
                        query.setParameter(key, params.get(key));
                    }
                }

                List<Object> ret = query.getResultList();
                result.setSingleValue(ret.get(0));
            }
            else if (item.getDataType().equalsIgnoreCase("Chart")) {
            
                Query query = entityManager.createNativeQuery(item.getSqlQuery());
        
                if (params != null) {

                    for (String key : params.keySet()) {
                        query.setParameter(key, params.get(key));
                    }
                }

                List<Object[]> ret = query.getResultList();
                
                result.setTimeLabels(new ArrayList<String>());
                result.setSeries(new ArrayList<>());
                
                for (Object[] row : ret) {
                
                    if (row.length >= 3) {
                    
                        if (result.getTimeLabels().indexOf(row[0]) < 0)
                            result.getTimeLabels().add(String.valueOf(row[0]));
                    
                        DashboardSerieDTO serie = null;
                        
                        for (DashboardSerieDTO tmp : result.getSeries()) {
                            
                            if (tmp.getLabel().equals(row[1])) {
                                
                                serie = tmp;
                                break;
                            }
                        }
                        
                        if (serie == null) {
                            
                            serie = new DashboardSerieDTO();
                            serie.setLabel(String.valueOf(row[1]));
                            serie.setValues(new ArrayList<>());
                            result.getSeries().add(serie);
                        }
                        
                        serie.getValues().add(row[2]);
                    }
                }
            }
            else if (item.getDataType().equalsIgnoreCase("DTO")) {
            
                SQLQuery query = entityManager.createNativeQuery(item.getSqlQuery()).unwrap(NativeQuery.class);
        
                if (params != null) {

                    for (String key : params.keySet()) {
                        query.setParameter(key, params.get(key));
                    }
                }
        
                query.setResultTransformer(Transformers.aliasToBean(Class.forName(item.getTargetClass())));

                List ret = query.getResultList();
            }
        }
        else
            throw new Exception("Dashboard item is not found");
        
        return result;
    }
}
