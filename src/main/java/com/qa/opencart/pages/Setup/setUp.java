package com.qa.opencart.pages.Setup;

import com.microsoft.playwright.*;


import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import utils.ConfigFile;

import org.apache.log4j.Logger;

import static java.util.logging.Logger.getLogger;

public class setUp {
    public static Playwright playwright;
    public static APIRequest request;
    public static APIRequestContext requestContext;
    Browser browser;
    public static BrowserContext browserContext;
    public static Page page;
    public static APIRequestContext api;
    //Initialize Log4j instance
    private static Logger Log = Logger.getLogger(setUp.class);
    String browserType;

    public Page initBrowser() throws IOException, URISyntaxException {
        Log.info(">>>>>>>>>" + System.getProperty("tagArg"));

            browserType = System.getProperty("browser");
            playwright = Playwright.create();

            //get Width and height of your screen using java
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();
            Log.info("\t Screen size is: " + width + ":" + height);

            switch (browserType) {
                case "chrome":
                    Log.info("\t Initializing chrome browser");
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setSlowMo(100).setHeadless(false));
                    break;
                case "firefox":
                    Log.info("\t Initializing firefox browser");
                    browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
                    break;
                case "safari":
                    Log.info("\t Initializing safari browser");
                    browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
                    break;
                case "ie":
                    Log.info("\t Initializing MS-Edge browser");
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setSlowMo(100).setHeadless(false));
                    break;

                default:
                    Log.fatal("\t please pass the right browser name.......");
                    break;
            }

            browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height).setRecordVideoDir(Paths.get("Videos/")).setRecordVideoSize(width, height));
            // Start tracing before creating / navigating a page.
            browserContext.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
            page = browserContext.newPage();
            Log.info("\t Navigating to url: " + ConfigFile.getUrl("ENVIRONMENT"));
//        page.navigate(ConfigFile.getUrl("ENVIRONMENT"));
            return page;
        }


}
