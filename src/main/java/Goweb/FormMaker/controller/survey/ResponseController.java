package Goweb.FormMaker.controller.survey;

import Goweb.FormMaker.dto.survey.CreateResponseDto;
import Goweb.FormMaker.domain.survey.Response;
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

/*    @PutMapping("/{responseId}")
    @Operation(summary = "특정 사용자 응답 수정하기")
    public ResponseEntity<Response> updateResponse(@PathVariable Long responseId, @RequestBody CreateResponseDto createResponseDto) {
        Response updatedResponse = responseService.updateResponse(responseId, createResponseDto);
        return ResponseEntity.ok(updatedResponse);
    }*/
}

