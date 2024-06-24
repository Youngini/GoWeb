package Goweb.FormMaker.exception;

public class SurveyNotFoundException extends RuntimeException {
    public SurveyNotFoundException(Long surveyId) {
        super("Survey with id " + surveyId + " not found.");
    }
}