package org.Tshimologong.controller;

import io.javalin.Javalin;
import org.Tshimologong.service.SurveyService;

import java.util.Map;

public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController() {
        this.surveyService = new SurveyService();
    }

    public void routes(Javalin appServer) {
        appServer.post("/saveData", ctx -> {
            try {
                String fullNames = ctx.formParam("fullNames");
                String email = ctx.formParam("email");
                String dob = ctx.formParam("DOB");
                String contactNumb = ctx.formParam("contactNumb");
                String[] foods = ctx.formParams("food").toArray(new String[0]);
                int moviesRating = Integer.parseInt(Objects.requireNonNull(ctx.formParam("movies")));
                int radioRating = Integer.parseInt(Objects.requireNonNull(ctx.formParam("radio")));
                int eatOutRating = Integer.parseInt(Objects.requireNonNull(ctx.formParam("eat_out")));
                int watchTvRating = Integer.parseInt(Objects.requireNonNull(ctx.formParam("watch_tv")));

                surveyService.saveSurveyData(fullNames, email, dob, contactNumb, foods, moviesRating, radioRating, eatOutRating, watchTvRating);
                ctx.redirect("/index.html");
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("Internal Server Error");
            }
        });

        appServer.get("survey_results.html", ctx -> {
            try {
                Map<String, Object> model = surveyService.generateSurveyResults();
                ctx.render("survey_results.html", model);
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("Internal Server Error");
            }
        });
    }
}
