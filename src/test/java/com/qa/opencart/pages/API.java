package com.qa.opencart.pages;

import com.microsoft.playwright.*;
import com.qa.opencart.pages.Setup.setUp;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.options.RequestOptions;
import utils.ConfigFile;

import java.io.IOException;
import java.util.Arrays;

public class API {
    private Page page = setUp.page;
    private static Logger Log = Logger.getLogger(setUp.class);
//    private Page page = setUp.page;
//    private APIRequestContext api = setUp.api;
//    private APIRequestContext requestContexts = setUp.requestContext;
    APIRequest request;
    Playwright playwright;
    APIRequestContext requestContext;



    @When("I want to set URL as {string}")
    public void iWantToSetURLAs(String arg0) throws IOException {
        playwright = Playwright.create();
        request =  playwright.request();
        requestContext = request.newContext();

        APIResponse apiResponse = requestContext.get(ConfigFile.getUrl("ENVIRONMENT")+arg0,RequestOptions.create().setQueryParam("id","54980")
                .setQueryParam("status","inactive"));

        page.navigate(apiResponse.url());

        int statusCode = apiResponse.status();
        System.out.println("Status code is : " + statusCode);

        System.out.println(apiResponse.url());


        System.out.println("----print api json response----");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyRespose = jsonResponse.toPrettyString();
        System.out.println(">>>"+jsonPrettyRespose);

    }
}
