package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.domain.survey.*;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.dto.survey.*;
import Goweb.FormMaker.exception.SurveyNotFoundException;
import Goweb.FormMaker.repository.survey.OptionRepository;
import Goweb.FormMaker.repository.survey.QuestionRepository;
import Goweb.FormMaker.repository.survey.SurveyParticipationRepository;
import Goweb.FormMaker.repository.survey.SurveyRepository;
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

    public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository, OptionRepository optionRepository, UserRepository userRepository, SurveyParticipationRepository surveyParticipationRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
        this.surveyParticipationRepository = surveyParticipationRepository;
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
    public Survey getSurvey(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException(surveyId));
    }


    @Transactional
    public void activateSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));
        survey.setActivation(true);
        surveyRepository.save(survey);
    }

    @Transactional
    public void deleteSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));
        surveyRepository.delete(survey);
    }

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

}
