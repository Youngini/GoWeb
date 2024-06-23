package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.dto.survey.CreateResponseDto;
import Goweb.FormMaker.domain.survey.*;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.dto.survey.surveyResponses.SurveyResponseDto;
import Goweb.FormMaker.dto.survey.surveyResponses.SurveyQuestionDto;
import Goweb.FormMaker.dto.survey.surveyResponses.UserResponseDto;
import Goweb.FormMaker.dto.survey.userResponse.UserOptionDto;
import Goweb.FormMaker.dto.survey.userResponse.UserQuestionDto;
import Goweb.FormMaker.dto.survey.userResponse.UserSurveyDto;
import Goweb.FormMaker.exception.ResourceNotFoundException;
import Goweb.FormMaker.repository.survey.*;
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
    private final ResponseOptionRepository responseOptionRepository;

    public ResponseService(ResponseRepository responseRepository, UserRepository userRepository,
                           SurveyRepository surveyRepository, QuestionRepository questionRepository,
                           OptionRepository optionRepository, ResponseOptionRepository responseOptionRepository) {
        this.responseRepository = responseRepository;
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.responseOptionRepository = responseOptionRepository;
    }

    @Transactional
    public Response createResponse(Long surveyId, Long userId, List<CreateResponseDto> createResponseDtos) {
        SurveyParticipation surveyParticipation = new SurveyParticipation();
        surveyParticipation.setSurvey(surveyRepository.findById(surveyId).get());
        surveyParticipation.setUser(userRepository.findById(userId).get());

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
                Set<ResponseOption> options = new HashSet<>();
                for (Long optionId : createResponseDto.getResponseOptions()) {
                    ResponseOption responseOption = new ResponseOption();
                    responseOption.setResponse(response);
                    responseOption.setOption(optionRepository.findById(optionId).get());
                    options.add(responseOption);
                }
                response.setResponseOptions(options);
            }
            savedResponse = responseRepository.save(response);
        }
        return savedResponse;
    }

    @Transactional
    public UserSurveyDto getUserResponse(Long surveyId, Long userId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Response> responses = responseRepository.findBySurveyAndUser(survey, user);

        // 설문 조사 정보
        UserSurveyDto userSurveyDto = new UserSurveyDto();
        userSurveyDto.setTitle(survey.getTitle());
        userSurveyDto.setDescription(survey.getDescription());
        userSurveyDto.setStartDate(survey.getStartDate());
        userSurveyDto.setDueDate(survey.getDueDate());
        userSurveyDto.setHashtag(survey.getHashtag());

        // 질문들 설정
        List<UserQuestionDto> userQuestionDtos = new ArrayList<>();
        for (Response response : responses) {
            Question question = response.getQuestion();
            UserQuestionDto userQuestionDto = new UserQuestionDto();
            userQuestionDto.setQuestionId(question.getId());
            userQuestionDto.setNum(question.getNum());
            userQuestionDto.setContent(question.getContent());
            userQuestionDto.setQuestionType(question.getQuestionType());
            userQuestionDto.setImageUrl(question.getImageUrl());

            //질문 내 옵션 설정
            List<UserOptionDto> userOptionDtos = new ArrayList<>();
            for (Option option : question.getOptions()) {
                UserOptionDto userOptionDto = new UserOptionDto();
                userOptionDto.setOptionId(option.getId());
                userOptionDto.setName(option.getName());
                userOptionDto.setNum(option.getNum());
                userOptionDto.setImageUrl(option.getImageUrl());
                userOptionDtos.add(userOptionDto);
            }
            userQuestionDto.setOptions(userOptionDtos);

            // 사용자가 선택한 답변
            List<Long> selectedOptionIds = response.getResponseOptions().stream()
                    .map(ResponseOption::getOption)
                    .map(Option::getId)
                    .collect(Collectors.toList());
            userQuestionDto.setSelectedOptionIds(selectedOptionIds);

            userQuestionDtos.add(userQuestionDto);
        }

        userSurveyDto.setQuestions(userQuestionDtos);
        return userSurveyDto;
    }

    @Transactional
    public SurveyResponseDto getSurveyResponse(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));

        SurveyResponseDto surveyResponseDto = new SurveyResponseDto();
        surveyResponseDto.setTitle(survey.getTitle());
        surveyResponseDto.setDescription(survey.getDescription());
        surveyResponseDto.setStartDate(survey.getStartDate());
        surveyResponseDto.setDueDate(survey.getDueDate());
        surveyResponseDto.setActivation(survey.isActivation());
        surveyResponseDto.setHashtag(survey.getHashtag());

        List<SurveyQuestionDto> surveyQuestionDtos = new ArrayList<>();
        for (Question question : survey.getQuestions()) {
            SurveyQuestionDto surveyQuestionDto = new SurveyQuestionDto();
            surveyQuestionDto.setQuestionId(question.getId());
            surveyQuestionDto.setNum(question.getNum());
            surveyQuestionDto.setContent(question.getContent());
            surveyQuestionDto.setQuestionType(question.getQuestionType());
            surveyQuestionDto.setImageUrl(question.getImageUrl());

            List<UserResponseDto> userResponseDtos = new ArrayList<>();

            for (Response response : responseRepository.findByQuestionId(question.getId())) {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setStudentId(response.getUser().getId());
                userResponseDto.setStudentName(response.getUser().getName());
                userResponseDto.setResponse(response.getResponseOptions().stream()
                        .map(ResponseOption::getOption)
                        .map(Option::getName)
                        .collect(Collectors.toList()));
                userResponseDtos.add(userResponseDto);
            }
            surveyQuestionDto.setSelectedOptions(userResponseDtos);
            surveyQuestionDtos.add(surveyQuestionDto);
        }
        surveyResponseDto.setQuestions(surveyQuestionDtos);

        return surveyResponseDto;
    }

    @Transactional
    public Response updateResponse(Long surveyId, Long userId, List<CreateResponseDto> createResponseDtos) {
        // 기존 응답 찾기
        Response existingResponse = responseRepository.findBySurveyIdAndUserId(surveyId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found"));

        // 응답 옵션 삭제
        existingResponse.getResponseOptions().clear();
        responseOptionRepository.deleteAllByResponse(existingResponse);

        // 새로운 응답 정보 설정
        for (CreateResponseDto createResponseDto : createResponseDtos) {
            // 질문 가져오기
            Question question = questionRepository.findById(createResponseDto.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

            // 선택지 연결
            if (question.getOptions() == null || question.getOptions().isEmpty()) {
                existingResponse.setAnswer(createResponseDto.getAnswer());
            } else {
                Set<ResponseOption> options = new HashSet<>();
                for (Long optionId : createResponseDto.getResponseOptions()) {
                    ResponseOption responseOption = new ResponseOption();
                    responseOption.setResponse(existingResponse);
                    responseOption.setOption(optionRepository.findById(optionId).get());
                    options.add(responseOption);
                }
                existingResponse.setResponseOptions(options);
            }
        }

        // 응답 저장
        return responseRepository.save(existingResponse);
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

