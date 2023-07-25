package com.qa.opencart.pages;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;

import com.qa.opencart.pages.Setup.setUp;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Hook {
    private static Logger Log = Logger.getLogger(setUp.class);
    setUp pf;
    Scenario scenario;
    Page page;

    @Before
    public void setUp(Scenario scenario) throws IOException, URISyntaxException, InstantiationException, IllegalAccessException {

        Log.info("\t In Hooks setUp Method");
        pf = new setUp();
        page = pf.initBrowser();
    }

    @After
    public void cleanUp(Scenario scenario) throws IOException, InstantiationException, IllegalAccessException {
        List<String> almTestCase = null;
        String testResult = "Passed";
        String scenarioName = scenario.getName().replace("\"", "");

        Log.info("\t In Hooks cleanUp Method");

        if (scenario.isFailed() == true) {
            // Screenshot to add to report
            testResult = "Failed";
            byte[] screenShot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            scenario.attach(screenShot, "image/png", scenarioName);
        }

        // Stop tracing and export it into a zip archive.
        setUp.browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("TraceViewer/trace.zip")));

        //--------------------------------------------------------------------------------------------------------
        //Upload test status in ALM
        // Find tag starting with @ALM_
        almTestCase = scenario.getSourceTagNames().stream().filter(name -> name.matches("(^@ALM_).*")).collect(Collectors.toList());
        if (almTestCase.size() > 0) {
            String almtag = almTestCase.get(0).substring(5);

            if (System.getProperty("result.env") != null) {
                if ((System.getProperty("result.env").trim().equalsIgnoreCase("ALM")) && (System.getProperty("alm.testSet") != null) && (almtag != null)) {
                    Log.info("\t Trying to update status in ALM...");

                    //To run groovy file in java class, this is how it's done using GroovyClassLoader method
                    final GroovyClassLoader classLoader = new GroovyClassLoader();
                    // Load Groovy script file.
                    Class groovy = classLoader.parseClass(new File("src/main/java/com/qa/opencart/helpers/alm.groovy"));
                    GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
                    groovyObj.invokeMethod("UpdateALMTestResult", new Object[]{System.getProperty("alm.testSet"), almtag, testResult});
                }
            }

        }
        page.close();
        setUp.browserContext.close();
//        setUp.playwright.close();


    }
}
