package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.domain.survey.*;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.dto.survey.*;
import Goweb.FormMaker.dto.survey.createSurvey.CreateOptionDto;
import Goweb.FormMaker.dto.survey.createSurvey.CreateQuestionDto;
import Goweb.FormMaker.dto.survey.createSurvey.CreateSurveyDto;
import Goweb.FormMaker.dto.survey.loadSurvey.LoadOptionDto;
import Goweb.FormMaker.dto.survey.loadSurvey.LoadQuestionDto;
import Goweb.FormMaker.dto.survey.loadSurvey.LoadSurveyDto;
import Goweb.FormMaker.dto.survey.updateSurvey.UpdateOptionDto;
import Goweb.FormMaker.dto.survey.updateSurvey.UpdateQuestionDto;
import Goweb.FormMaker.dto.survey.updateSurvey.UpdateSurveyDto;
import Goweb.FormMaker.exception.ResourceNotFoundException;
import Goweb.FormMaker.exception.SurveyNotFoundException;
import Goweb.FormMaker.repository.survey.*;
import Goweb.FormMaker.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;
    private final SurveyParticipationRepository surveyParticipationRepository;
    private final ResponseRepository responseRepository;

    public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository, OptionRepository optionRepository, UserRepository userRepository, SurveyParticipationRepository surveyParticipationRepository, ResponseRepository responseRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
        this.surveyParticipationRepository = surveyParticipationRepository;
        this.responseRepository = responseRepository;
    }

    @Transactional
    public Survey createSurvey(CreateSurveyDto createSurveyDto) {
        Survey survey = new Survey();
        survey.setTitle(createSurveyDto.getTitle());
        survey.setDescription(createSurveyDto.getDescription());
        survey.setStartDate(createSurveyDto.getStartDate());
        survey.setDueDate(createSurveyDto.getDueDate());
        survey.setActivation(createSurveyDto.isActivation());
        survey.setHashtag(createSurveyDto.getHashtag());

        List<Question> questions = new ArrayList<>();
        for (CreateQuestionDto createQuestionDto : createSurveyDto.getQuestions()) {
            Question question = new Question();
            question.setNum(createQuestionDto.getOrder());
            question.setContent(createQuestionDto.getContent());
            question.setQuestionType(createQuestionDto.getQuestionType());
            question.setImageUrl(createQuestionDto.getImageUrl());
            question.setSurvey(survey);

            List<Option> options = new ArrayList<>();
            for (CreateOptionDto createOptionDto : createQuestionDto.getOptions()) {
                Option option = new Option();
                option.setName(createOptionDto.getName());
                option.setNum(createOptionDto.getOrder());
                option.setImageUrl(createOptionDto.getImageUrl());
                option.setQuestion(question);
                options.add(option);
            }
            question.setOptions(options);
            questions.add(question);
        }
        survey.setQuestions(questions);

        return surveyRepository.save(survey);
    }

    @Transactional
    public List<SurveyListDto> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        List<SurveyListDto> surveyListDtos = new ArrayList<>();
        for (Survey survey : surveys) {
            SurveyListDto surveyListDto = new SurveyListDto();
            surveyListDto.setId(survey.getId());
            surveyListDto.setTitle(survey.getTitle());
            surveyListDto.setStartDate(survey.getStartDate());
            surveyListDto.setDueDate(survey.getDueDate());
            surveyListDto.setActivation(survey.isActivation());
            surveyListDto.setHashtag(survey.getHashtag());
            surveyListDtos.add(surveyListDto);
        }
        return surveyListDtos;
    }

    @Transactional
    public LoadSurveyDto getSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException(surveyId));

        LoadSurveyDto SurveyDto = new LoadSurveyDto();

        List<LoadQuestionDto> QuestionDtos = new ArrayList<>();

        for(Question question : survey.getQuestions()) {
            LoadQuestionDto dto = new LoadQuestionDto();
            dto.setQuestionId(question.getId());
            dto.setContent(question.getContent());
            dto.setNum(question.getNum());
            dto.setQuestionType(question.getQuestionType());
            dto.setImageUrl(question.getImageUrl());

            List<LoadOptionDto> OptionDtos = new ArrayList<>();
            for(Option option : question.getOptions()) {
                LoadOptionDto dto2 = new LoadOptionDto();
                dto2.setOptionId(option.getId());
                dto2.setName(option.getName());
                dto2.setNum(option.getNum());
                dto2.setImageUrl(option.getImageUrl());

                OptionDtos.add(dto2);
            }
            dto.setOptions(OptionDtos);

            QuestionDtos.add(dto);
        }

        SurveyDto.setTitle(survey.getTitle());
        SurveyDto.setDescription(survey.getDescription());
        SurveyDto.setStartDate(survey.getStartDate());
        SurveyDto.setDueDate(survey.getDueDate());
        SurveyDto.setActivation(survey.isActivation());
        SurveyDto.setHashtag(survey.getHashtag());
        SurveyDto.setQuestions(QuestionDtos);

        return SurveyDto;
    }


    @Transactional
    public void activateSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));
        if(survey.isActivation()) {
            survey.setActivation(false);
        }
        else{
            survey.setActivation(true);
        }
        surveyRepository.save(survey);
    }

    @Transactional
    public void deleteSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

        // 1. Response 테이블의 데이터 삭제
        List<Response> responses = responseRepository.findBySurvey(survey);
        responseRepository.deleteAll(responses);

        // 2. Question 테이블의 데이터 삭제
        List<Question> questions = questionRepository.findBySurvey(survey);
        questionRepository.deleteAll(questions);

        // 3. Survey 테이블의 데이터 삭제
        surveyRepository.delete(survey);
    }

    
    //  excel
    @Transactional
    public List<ExcelSurveyResponse> getAllResponse(Long surveyId) {
        List<ExcelSurveyResponse> data = new ArrayList<>();

        // 특정 설문조사에 대한 모든 참여 정보 가져오기
        List<SurveyParticipation> surveyParticipations = surveyParticipationRepository.findBySurveyId(surveyId);

        // 참여 정보에서 필요한 데이터 추출하여 ExcelSurveyResponse 객체 생성
        for (SurveyParticipation participation : surveyParticipations) {
            User user = participation.getUser();
            List<UserResponseDto> selectedAnswers = new ArrayList<>();

            // 참여자가 선택한 답변 정보 가져오기


            ExcelSurveyResponse excelResponse = new ExcelSurveyResponse();
            excelResponse.setName(user.getName());
            excelResponse.setStudentNumber(user.getStudentNumber());
            excelResponse.setSelectedAnswers(selectedAnswers);

            data.add(excelResponse);
        }

        return data;
    }

    @Transactional
    public LoadSurveyDto updateSurvey(Long surveyId, UpdateSurveyDto updateSurveyDto) {
        // 기존 설문조사 정보 가져오기
        Survey existingSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));

        // 설문조사 정보 업데이트
        existingSurvey.setTitle(updateSurveyDto.getTitle());
        existingSurvey.setDescription(updateSurveyDto.getDescription());
        existingSurvey.setStartDate(updateSurveyDto.getStartDate());
        existingSurvey.setDueDate(updateSurveyDto.getDueDate());
        existingSurvey.setActivation(updateSurveyDto.isActivation());
        existingSurvey.setHashtag(updateSurveyDto.getHashtag());

        // 기존 질문 삭제
        questionRepository.deleteAllBySurvey(existingSurvey);

        // 새로운 질문 생성
        List<Question> questions = new ArrayList<>();
        for (UpdateQuestionDto updateQuestionDto : updateSurveyDto.getQuestions()) {
            Question question = new Question();
            question.setNum(updateQuestionDto.getOrder());
            question.setContent(updateQuestionDto.getContent());
            question.setQuestionType(updateQuestionDto.getQuestionType());
            question.setImageUrl(updateQuestionDto.getImageUrl());
            question.setSurvey(existingSurvey);

            List<Option> options = new ArrayList<>();
            for (UpdateOptionDto updateOptionDto : updateQuestionDto.getOptions()) {
                Option option = new Option();
                option.setName(updateOptionDto.getName());
                option.setNum(updateOptionDto.getOrder());
                option.setImageUrl(updateOptionDto.getImageUrl());
                option.setQuestion(question);
                options.add(option);
            }
            question.setOptions(options);
            questions.add(question);
        }
        existingSurvey.setQuestions(questions);

        // 업데이트된 설문조사 저장
        Survey updatedSurvey = surveyRepository.save(existingSurvey);

        // LoadSurveyDto로 변환
        return convertToLoadSurveyDto(updatedSurvey);
    }

    private LoadSurveyDto convertToLoadSurveyDto(Survey survey) {
        LoadSurveyDto loadSurveyDto = new LoadSurveyDto();
        loadSurveyDto.setTitle(survey.getTitle());
        loadSurveyDto.setDescription(survey.getDescription());
        loadSurveyDto.setStartDate(survey.getStartDate());
        loadSurveyDto.setDueDate(survey.getDueDate());
        loadSurveyDto.setActivation(survey.isActivation());
        loadSurveyDto.setHashtag(survey.getHashtag());

        List<LoadQuestionDto> loadQuestionDtos = new ArrayList<>();
        for (Question question : survey.getQuestions()) {
            LoadQuestionDto loadQuestionDto = new LoadQuestionDto();
            loadQuestionDto.setNum(question.getNum());
            loadQuestionDto.setContent(question.getContent());
            loadQuestionDto.setQuestionType(question.getQuestionType());
            loadQuestionDto.setImageUrl(question.getImageUrl());

            List<LoadOptionDto> loadOptionDtos = new ArrayList<>();
            for (Option option : question.getOptions()) {
                LoadOptionDto loadOptionDto = new LoadOptionDto();
                loadOptionDto.setNum(option.getNum());
                loadOptionDto.setName(option.getName());
                loadOptionDto.setImageUrl(option.getImageUrl());
                loadOptionDtos.add(loadOptionDto);
            }
            loadQuestionDto.setOptions(loadOptionDtos);
            loadQuestionDtos.add(loadQuestionDto);
        }
        loadSurveyDto.setQuestions(loadQuestionDtos);

        return loadSurveyDto;
    }

}
