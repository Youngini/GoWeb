package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.dto.survey.CreateResponseDto;
import Goweb.FormMaker.domain.survey.*;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.dto.survey.LoadSurveyUserResponse.LoadOptionDto;
import Goweb.FormMaker.dto.survey.LoadSurveyUserResponse.LoadQuestionDto;
import Goweb.FormMaker.dto.survey.LoadSurveyUserResponse.LoadUserSurvey;
import Goweb.FormMaker.exception.ResourceNotFoundException;
import Goweb.FormMaker.exception.ResponseNotFoundException;
import Goweb.FormMaker.repository.survey.OptionRepository;
import Goweb.FormMaker.repository.survey.QuestionRepository;
import Goweb.FormMaker.repository.survey.ResponseRepository;
import Goweb.FormMaker.repository.survey.SurveyRepository;
import Goweb.FormMaker.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Response createResponse(Long surveyId, Long userId, List<CreateResponseDto> createResponseDtos) {
        Response savedResponse = null;
        for (CreateResponseDto createResponseDto : createResponseDtos) {
            // 질문 가져오기
            Question question = questionRepository.findById(createResponseDto.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

            Response response = new Response();

            // 사용자 설정
            response.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found")));

            // 설문조사 연결
            response.setSurvey(surveyRepository.findById(surveyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Survey not found")));

            // 질문 연결
            response.setQuestion(question);

            // 선택지 연결
            if (question.getOptions() == null || question.getOptions().isEmpty()) {
                response.setAnswer(createResponseDto.getAnswer());
            } else {
                Set<ResponseOption> responseOptions = new HashSet<>();
                for (Integer optionId : createResponseDto.getResponseOptions()) {
                    ResponseOption responseOption = new ResponseOption();
                    responseOption.setResponse(response);
                    responseOption.setOption(question.getOptions().stream()
                            .filter(o -> o.getId().equals(optionId))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Option not found")));

                    responseOptions.add(responseOption);
                }
                response.setResponseOptions(responseOptions);
            }
            savedResponse = responseRepository.save(response);
        }
        return savedResponse;
    }

    public LoadUserSurvey getUserResponse(Long surveyId, Long userId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Response> responses = responseRepository.findBySurveyAndUser(survey, user);

        // 설문 조사 정보
        LoadUserSurvey loadUserSurvey = new LoadUserSurvey();
        loadUserSurvey.setTitle(survey.getTitle());
        loadUserSurvey.setDescription(survey.getDescription());
        loadUserSurvey.setStartDate(survey.getStartDate());
        loadUserSurvey.setDueDate(survey.getDueDate());
        loadUserSurvey.setHashtag(survey.getHashtag());

        // 질문들 설정
        List<LoadQuestionDto> loadQuestionDtos = new ArrayList<>();
        for (Response response : responses) {
            Question question = response.getQuestion();
            LoadQuestionDto loadQuestionDto = new LoadQuestionDto();
            loadQuestionDto.setQuestionId(question.getId());
            loadQuestionDto.setNum(question.getNum());
            loadQuestionDto.setContent(question.getContent());
            loadQuestionDto.setQuestionType(question.getQuestionType());
            loadQuestionDto.setImageUrl(question.getImageUrl());

            //질문 내 옵션 설정
            List<LoadOptionDto> loadOptionDtos = new ArrayList<>();
            for (Option option : question.getOptions()) {
                LoadOptionDto loadOptionDto = new LoadOptionDto();
                loadOptionDto.setOptionId(option.getId());
                loadOptionDto.setName(option.getName());
                loadOptionDto.setNum(option.getNum());
                loadOptionDto.setImageUrl(option.getImageUrl());
                loadOptionDtos.add(loadOptionDto);
            }
            loadQuestionDto.setOptions(loadOptionDtos);

            // 사용자가 선택한 질문
            List<Long> selectedOptionIds = response.getResponseOptions().stream()
                    .map(ro -> ro.getOption().getId())
                    .collect(Collectors.toList());
            loadQuestionDto.setSelectedOptionIds(selectedOptionIds);

            loadQuestionDtos.add(loadQuestionDto);
        }

        loadUserSurvey.setQuestions(loadQuestionDtos);
        return loadUserSurvey;
    }


    @Transactional
    public List<Response> getSurveyResponse(Long surveyId) {
        return responseRepository.findBySurveyId(surveyId);
    }

    /*@Transactional
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
    }*/


}

