package Goweb.FormMaker.service.excel;

import Goweb.FormMaker.domain.survey.*;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.dto.survey.ExcelSurveyResponse;
import Goweb.FormMaker.repository.survey.ResponseRepository;
import Goweb.FormMaker.repository.survey.SurveyParticipationRepository;
import Goweb.FormMaker.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelService {
    private final SurveyParticipationRepository surveyParticipationRepository;
    private final ResponseRepository responseRepository;
    private final SurveyRepository surveyRepository;

    // 엑셀 파일 생성 및 클라이언트에게 전송하는 메소드
    public ByteArrayInputStream exportSurveyDataToExcel(Long surveyId, List<User> users, List<Question> questions) throws IOException {
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
        createDataRows(sheet, surveyId ,users, questions);

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

    private void createDataRows(Sheet sheet, Long surveyId ,List<User> users, List<Question> questions) {
        int rowIndex = 1;

        for (User user : users) {
            Row row = sheet.createRow(rowIndex++);
            Survey survey = surveyRepository.getReferenceById(surveyId);
            List<Response> responses = responseRepository.findBySurveyAndUser(survey, user);

            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getStudentNumber());

            int j = 3;
            for (Response response : responses) {
                row.createCell(0).setCellValue(response.getUpdatedAt());

                if (response.getAnswer() != null)
                    row.createCell(j++).setCellValue(response.getAnswer());
                else{
                    String responseOptionNames = response.getResponseOptions().stream()
                            .map(ResponseOption::getOption)
                            .map(Option::getName)
                            .collect(Collectors.joining(", "));
                    row.createCell(j++).setCellValue(responseOptionNames);
                }
            }

            // 열 너비 자동 조정
            for (int i = 0; i < 3 + questions.size() + 1; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    public List<User> findUserbySurveyId(Long surveyId) {
        List<SurveyParticipation> surveyParticipations = surveyParticipationRepository.findBySurveyId(surveyId);
        List<User> users = surveyParticipations.stream()
                .map(SurveyParticipation::getUser)
                .collect(Collectors.toList());
        return users;
    }
}