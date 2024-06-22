package Goweb.FormMaker.controller.excel;

import Goweb.FormMaker.domain.survey.Question;
import Goweb.FormMaker.dto.survey.ExcelSurveyResponse;
import Goweb.FormMaker.service.excel.ExcelService;
import Goweb.FormMaker.service.survey.QuestionService;
import Goweb.FormMaker.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("excels/")
public class ExcelController {

    private final ExcelService excelService;
    private final QuestionService questionService; // Question 데이터를 가져오기 위한 서비스
    private final SurveyService surveyService;

    @GetMapping("/surveyDownload/{surveyId}")
    public ResponseEntity<InputStreamResource> downloadSurveyExcel(@PathVariable Long surveyId) {
        try {
            List<ExcelSurveyResponse> data = surveyService.getAllResponse(surveyId); // 특정 설문조사에 대한 모든 응답 가져오기
            List<Question> questions = questionService.getAllQuestions(surveyId); // 모든 질문 가져오기

            ByteArrayInputStream result = excelService.exportSurveyDataToExcel(data, questions);

            String fileName = "SurveyData_" + LocalDate.now().toString() + ".xlsx";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/vnd.ms-excel");
            headers.add("Content-Disposition", "attachment; filename=" + fileName);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new InputStreamResource(result));
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        // 예외 발생 시 반환
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}