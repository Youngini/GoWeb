package Goweb.FormMaker.controller.survey;

import Goweb.FormMaker.dto.survey.CreateSurveyDto;
import Goweb.FormMaker.dto.survey.SurveyListDto;
import Goweb.FormMaker.domain.survey.Survey;
import Goweb.FormMaker.service.survey.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    @Operation(summary = "새로운 설문조사를 생성")
    public ResponseEntity<Survey> createSurvey(@RequestBody @Valid CreateSurveyDto createSurveyDto) {
        Survey savedSurvey = surveyService.createSurvey(createSurveyDto);
        return new ResponseEntity<>(savedSurvey, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "모든 설문조사 불러오기")
    public ResponseEntity<List<SurveyListDto>> getSurveys() {
        List<SurveyListDto> surveyListDtos = surveyService.getAllSurveys();
        return new ResponseEntity<>(surveyListDtos, HttpStatus.OK);
    }

    @GetMapping("/{surveyId}")
    @Operation(summary = "특정 설문조사 내용 불러오기")
    public ResponseEntity<Survey> getSurvey(@PathVariable Long surveyId) {
        Survey survey = surveyService.getSurvey(surveyId);
        return ResponseEntity.ok(survey);
    }

    @PutMapping("/{surveyId}/activate")
    @Operation(summary = "특정 설문조사를 활성화")
    public ResponseEntity<Void> activateSurvey(@PathVariable Long surveyId) {
        surveyService.activateSurvey(surveyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{surveyId}")
    @Operation(summary = "설문조사를 삭제")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long surveyId) {
        surveyService.deleteSurvey(surveyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
