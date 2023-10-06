package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ru.otus.services.TemplateProcessor;

public class AdminPageServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin_page.html";
    private final TemplateProcessor templateProcessor;

    public AdminPageServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
    }
}
