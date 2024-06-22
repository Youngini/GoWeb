package Goweb.FormMaker.controller.survey;

import Goweb.FormMaker.DTO.survey.CreateResponseDto;
import Goweb.FormMaker.domain.survey.Response;
import Goweb.FormMaker.service.survey.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{responseId}")
    @Operation(summary = "특정 사용자 응답 가져오기")
    public ResponseEntity<Response> getResponse(@PathVariable Long responseId) {
        Response response = responseService.getResponse(responseId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{responseId}")
    @Operation(summary = "특정 사용자 응답 수정하기")
    public ResponseEntity<Response> updateResponse(@PathVariable Long responseId, @RequestBody CreateResponseDto createResponseDto) {
        Response updatedResponse = responseService.updateResponse(responseId, createResponseDto);
        return ResponseEntity.ok(updatedResponse);
    }
}

