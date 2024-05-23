package org.Tshimologong;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.Tshimologong.controller.SurveyController;

public class Server {
    public static void main(String[] args) {
        Javalin appServer = Javalin.create(config -> {
            config.addStaticFiles("/html", Location.CLASSPATH);
            configureTemplateEngine();
        }).start(5050);

        SurveyController surveyController = new SurveyController();
        surveyController.routes(appServer);
    }

    private static void configureTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/html/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateEngine.setTemplateResolver(templateResolver);
        JavalinThymeleaf.configure(templateEngine);
    }
}
