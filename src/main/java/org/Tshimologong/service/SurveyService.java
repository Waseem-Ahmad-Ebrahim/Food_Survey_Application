package org.Tshimologong.service;

import org.Tshimologong.repository.SurveyRepository;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService() {
        this.surveyRepository = new SurveyRepository();
    }

    public void saveSurveyData(String fullNames, String email, String dob, String contactNumb, String[] foods, int moviesRating, int radioRating, int eatOutRating, int watchTvRating) {
        surveyRepository.saveToDatabase(fullNames, email, dob, contactNumb, foods, moviesRating, radioRating, eatOutRating, watchTvRating);
    }

    public Map<String, Object> generateSurveyResults() {
        Map<String, Object> model = new HashMap<>();
        var surveyData = surveyRepository.getSurveyData();

        int totalSurveys = surveyData.size();
        int totalAge = 0;
        int oldestAge = Integer.MIN_VALUE;
        int youngestAge = Integer.MAX_VALUE;
        int totalPizza = 0;
        int totalPasta = 0;
        int totalPapAndWors = 0;
        int totalMoviesRating = 0;
        int totalRadioRating = 0;
        int totalEatOutRating = 0;
        int totalWatchTvRating = 0;

        for (var survey : surveyData) {
            totalAge += survey.age;
            oldestAge = Math.max(oldestAge, survey.age);
            youngestAge = Math.min(youngestAge, survey.age);

            if (survey.favoriteFood.contains("Pizza")) totalPizza++;
            if (survey.favoriteFood.contains("Pasta")) totalPasta++;
            if (survey.favoriteFood.contains("Pap and Wors")) totalPapAndWors++;

            totalMoviesRating += survey.movies;
            totalRadioRating += survey.radio;
            totalEatOutRating += survey.eatOut;
            totalWatchTvRating += survey.watchTv;
        }

        DecimalFormat df = new DecimalFormat("#.#");

        if (totalSurveys > 0) {
            model.put("totalSurveys", totalSurveys);
            model.put("avgAge", df.format((double) totalAge / totalSurveys));
            model.put("oldestAge", oldestAge);
            model.put("youngestAge", youngestAge);
            model.put("percentPizza", df.format((double) totalPizza / totalSurveys * 100));
            model.put("percentPasta", df.format((double) totalPasta / totalSurveys * 100));
            model.put("percentPapAndWors", df.format((double) totalPapAndWors / totalSurveys * 100));
            model.put("avgMoviesRating", df.format((double) totalMoviesRating / totalSurveys));
            model.put("avgRadioRating", df.format((double) totalRadioRating / totalSurveys));
            model.put("avgEatOutRating", df.format((double) totalEatOutRating / totalSurveys));
            model.put("avgWatchTvRating", df.format((double) totalWatchTvRating / totalSurveys));
            model.put("noSurveys", false);
        } else {
            model.put("noSurveys", true);
        }

        return model;
    }
}
