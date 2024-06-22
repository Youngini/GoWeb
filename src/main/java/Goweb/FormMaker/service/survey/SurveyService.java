package Goweb.FormMaker.service.survey;

import Goweb.FormMaker.DTO.Option.CreateOptionDto;
import Goweb.FormMaker.DTO.question.CreateQuestionDto;
import Goweb.FormMaker.DTO.survey.CreateSurveyDto;
import Goweb.FormMaker.DTO.survey.SurveyListDto;
import Goweb.FormMaker.domain.survey.Option;
import Goweb.FormMaker.domain.survey.Question;
import Goweb.FormMaker.domain.survey.Survey;
import Goweb.FormMaker.exception.SurveyNotFoundException;
import Goweb.FormMaker.repository.survey.OptionRepository;
import Goweb.FormMaker.repository.survey.QuestionRepository;
import Goweb.FormMaker.repository.survey.SurveyRepository;
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

    public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository, OptionRepository optionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
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
            question.setOrder(createQuestionDto.getOrder());
            question.setContent(createQuestionDto.getContent());
            question.setQuestionType(createQuestionDto.getQuestionType());
            question.setImageUrl(createQuestionDto.getImageUrl());
            question.setSurvey(survey);

            List<Option> options = new ArrayList<>();
            for (CreateOptionDto createOptionDto : createQuestionDto.getOptions()) {
                Option option = new Option();
                option.setName(createOptionDto.getName());
                option.setOrder(createOptionDto.getOrder());
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

}
