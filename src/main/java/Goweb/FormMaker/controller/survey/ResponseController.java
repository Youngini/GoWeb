package Goweb.FormMaker.controller.survey;

import Goweb.FormMaker.dto.survey.CreateResponseDto;
import Goweb.FormMaker.domain.survey.Response;
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

    @PostMapping
    @Operation(summary = "응답하기")
    public ResponseEntity<Response> createResponse(@RequestBody CreateResponseDto createResponseDto) {
        Response savedResponse = responseService.createResponse(createResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResponse);
    }

    @GetMapping("/user/{responseId}")
    @Operation(summary = "특정 사용자 응답 가져오기")
    public ResponseEntity<Response> getResponse(@PathVariable Long responseId) {
        Response response = responseService.getUserResponse(responseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/survey/{surveyId}")
    @Operation(summary = "특정 설문조사의 모든 응답 가져오기")
    public ResponseEntity<List<Response>> getSurveyResponse(@PathVariable Long surveyId) {
        List<Response> responses = responseService.getSurveyResponse(surveyId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{responseId}")
    @Operation(summary = "특정 사용자 응답 수정하기")
    public ResponseEntity<Response> updateResponse(@PathVariable Long responseId, @RequestBody CreateResponseDto createResponseDto) {
        Response updatedResponse = responseService.updateResponse(responseId, createResponseDto);
        return ResponseEntity.ok(updatedResponse);
    }
}

