package Goweb.FormMaker.service.excel;

import Goweb.FormMaker.domain.survey.Question;
import Goweb.FormMaker.dto.survey.ExcelSurveyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelService {

    // 엑셀 파일 생성 및 클라이언트에게 전송하는 메소드
    public ByteArrayInputStream exportSurveyDataToExcel(List<ExcelSurveyResponse> surveyDataList, List<Question> questions) throws IOException {
        // 1. Create File
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // 2. 스타일 설정
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 3. 시트 생성
        Sheet sheet = workbook.createSheet("설문조사 응답 모음");

        // 4. 헤더 생성
        createHeaderRow(sheet, questions, headerCellStyle);

        // 5. 데이터 행 생성
        createDataRows(sheet, surveyDataList, questions);

        // 6. 파일 작성
        workbook.write(out);

        workbook.close();
        log.info("[ExcelService:getResponseToExcel] create response report done. row count:[{}]", questions.size() + 5);
        return new ByteArrayInputStream(out.toByteArray());

/*        // 다운로드 설정
        String fileName = "낙상 설문조사 응답 모음";
        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
        ServletOutputStream outputStream = res.getOutputStream();
        outputStream.write(out.toByteArray());
        outputStream.flush();
        outputStream.close();*/
    }

    private void createHeaderRow(Sheet sheet, List<Question> questions, CellStyle headerCellStyle) {
        Row headerRow = sheet.createRow(0);

        int columnIndex = 0;
        headerRow.createCell(columnIndex++).setCellValue("응답한 날짜");
        headerRow.createCell(columnIndex++).setCellValue("이름");
        headerRow.createCell(columnIndex++).setCellValue("학번");

        for (Question question : questions) {
            Cell cell = headerRow.createCell(columnIndex++);
            cell.setCellValue(question.getContent());
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void createDataRows(Sheet sheet, List<ExcelSurveyResponse> surveyDataList, List<Question> questions) {
        int rowIndex = 1;

        for (ExcelSurveyResponse data : surveyDataList) {
            Row row = sheet.createRow(rowIndex++);

/*
            row.createCell(0).setCellValue(data.getSurvey_id());
            row.createCell(1).setCellValue(data.getCreated_at());
            row.createCell(2).setCellValue(data.getSenior_id());
            row.createCell(3).setCellValue(data.getSenior_name());

            List<String> weights = data.getWeights();
            for (int i = 0; i < weights.size(); i++) {
                row.createCell(i + 4).setCellValue(weights.get(i));
            }

            row.createCell(weights.size() + 4).setCellValue(data.getRank());
        }
*/

            // 열 너비 자동 조정
            for (int i = 0; i < 4 + questions.size() + 1; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }
}