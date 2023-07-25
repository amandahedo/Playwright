package com.qa.opencart.pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GETAPICall {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup(){
        //create instance for server to initiate
        playwright = Playwright.create();
        request =  playwright.request();
        requestContext = request.newContext();
    }


    @Test

    public void getUsersAPItest() throws IOException {
//create instance for server to initiate

        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println("Status code is : " + statusCode);

        Assert.assertEquals(statusCode, 200);

        String statusGetResponse = apiResponse.statusText();
        System.out.println(statusGetResponse);
        //we need to convert json object - using json parsing
        System.out.println(apiResponse.text());

        //printing url
        System.out.println(apiResponse.url());

        System.out.println("----print api json response----");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyRespose = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyRespose);

        //printing response header AND validating
        Map<String,String> headerMap =apiResponse.headers();
        System.out.println(headerMap);
        Assert.assertEquals(headerMap.get("connection"),"close");
    }



    public void getSpecificUsers() throws IOException {


        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users",RequestOptions.create().setQueryParam("id","3810922")
                .setQueryParam("status","inactive"));
        int statusCode = apiResponse.status();
        System.out.println("Status code is : " + statusCode);

        System.out.println(apiResponse.url());

        System.out.println("----print api json response----");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyRespose = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyRespose);

    }
    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
