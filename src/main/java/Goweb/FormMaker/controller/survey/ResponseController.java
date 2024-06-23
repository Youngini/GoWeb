package Goweb.FormMaker.controller.survey;

import Goweb.FormMaker.dto.survey.CreateResponseDto;
import Goweb.FormMaker.domain.survey.Response;
import Goweb.FormMaker.dto.survey.SurveyListDto;
import Goweb.FormMaker.dto.survey.UserSurveyList;
import Goweb.FormMaker.dto.survey.surveyResponses.SurveyResponseDto;
import Goweb.FormMaker.dto.survey.userResponse.UserSurveyDto;
import Goweb.FormMaker.service.survey.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responses")
public class ResponseController {
    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping()
    @Operation(summary = "학생이 응답할 수 있는 설문 리스트 가져오기")
    public ResponseEntity<List<UserSurveyList>> getSurveys() {
        List<UserSurveyList> surveyListDtos = responseService.getAllSurveys();
        return new ResponseEntity<>(surveyListDtos, HttpStatus.OK);
    }

    @PostMapping("/{surveyId}/{userId}")
    @Operation(summary = "응답하기")
    public ResponseEntity<Response> createResponse(@PathVariable Long surveyId, @PathVariable Long userId, @RequestBody List<CreateResponseDto> createResponseDtos) {
        Response savedResponse = responseService.createResponse(surveyId, userId, createResponseDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResponse);
    }

    @GetMapping("/{surveyId}/{userId}")
    @Operation(summary = "특정 사용자 설문조사 가져오기")
    public ResponseEntity<UserSurveyDto> getUserResponse(@PathVariable Long surveyId, @PathVariable Long userId) {
        UserSurveyDto survey = responseService.getUserResponse(surveyId, userId);
        return ResponseEntity.ok(survey);
    }

    @GetMapping("/{surveyId}")
    @Operation(summary = "특정 설문조사의 모든 응답 가져오기")
    public ResponseEntity<SurveyResponseDto> getAllResponse(@PathVariable Long surveyId) {
        SurveyResponseDto surveyResponse = responseService.getSurveyResponse(surveyId);
        return ResponseEntity.ok(surveyResponse);
    }

    @PutMapping("/{surveyId}/{userId}")
    @Operation(summary = "특정 사용자 응답 수정하기")
    public ResponseEntity<Response> updateResponse(@PathVariable Long surveyId, @PathVariable Long userId, @RequestBody List<CreateResponseDto> createResponseDtos) {
        Response updatedResponse = responseService.updateResponse(surveyId, userId, createResponseDtos);
        return ResponseEntity.ok(updatedResponse);
    }
}

