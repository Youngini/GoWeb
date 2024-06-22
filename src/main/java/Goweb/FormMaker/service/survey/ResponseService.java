package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.DTO.survey.CreateResponseDto;
import Goweb.FormMaker.DTO.survey.CreateResponseOptionDto;
import Goweb.FormMaker.domain.survey.*;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.exception.ResponseNotFoundException;
import Goweb.FormMaker.repository.survey.OptionRepository;
import Goweb.FormMaker.repository.survey.QuestionRepository;
import Goweb.FormMaker.repository.survey.ResponseRepository;
import Goweb.FormMaker.repository.survey.SurveyRepository;
import Goweb.FormMaker.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public ResponseService(ResponseRepository responseRepository, UserRepository userRepository,
                           SurveyRepository surveyRepository, QuestionRepository questionRepository,
                           OptionRepository optionRepository) {
        this.responseRepository = responseRepository;
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public Response createResponse(CreateResponseDto createResponseDto) {
        // 사용자 조회
        User user = userRepository.findById(createResponseDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 설문조사 조회
        Survey survey = surveyRepository.findById(createResponseDto.getSurveyId())
                 .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        // 질문 조회
        Question question = questionRepository.findById(createResponseDto.getQuestionId())
                 .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        // Response 생성
        Response response = new Response();
        response.setUser(user);
        response.setSurvey(survey);
        response.setQuestion(question);
        response.setAnswer(createResponseDto.getAnswer());

        // ResponseOption 생성 및 설정
        Set<ResponseOption> responseOptions = new HashSet<>();
        for (CreateResponseOptionDto createResponseOptionDto : createResponseDto.getResponseOptions()) {
            Option option = optionRepository.findById(createResponseOptionDto.getOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("Option not found"));
            ResponseOption responseOption = new ResponseOption();
            responseOption.setResponse(response);
            responseOption.setOption(option);
            responseOptions.add(responseOption);
        }
        response.setResponseOptions(responseOptions);

        // 저장
        return responseRepository.save(response);
    }

    @Transactional
    public Response getResponse(Long responseId) {
        return responseRepository.findById(responseId)
                .orElseThrow(() -> new ResponseNotFoundException(responseId));
    }

    @Transactional
    public Response updateResponse(Long responseId, CreateResponseDto createResponseDto) {
        // 기존 Response 조회
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new IllegalArgumentException("Response not found"));

        // 사용자 조회
        User user = userRepository.findById(createResponseDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 설문조사 조회
        Survey survey = surveyRepository.findById(createResponseDto.getSurveyId())
                 .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        // 질문 조회
        Question question = questionRepository.findById(createResponseDto.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        // Response 업데이트
        response.setUser(user);
        response.setSurvey(survey);
        response.setQuestion(question);
        response.setAnswer(createResponseDto.getAnswer());

        // ResponseOption 업데이트
        Set<ResponseOption> responseOptions = new HashSet<>();
        for (CreateResponseOptionDto createResponseOptionDto : createResponseDto.getResponseOptions()) {
            Option option = optionRepository.findById(createResponseOptionDto.getOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("Option not found"));
            ResponseOption responseOption = new ResponseOption();
            responseOption.setResponse(response);
            responseOption.setOption(option);
            responseOptions.add(responseOption);
        }
        response.setResponseOptions(responseOptions);

        // 저장
        return responseRepository.save(response);
    }


}

