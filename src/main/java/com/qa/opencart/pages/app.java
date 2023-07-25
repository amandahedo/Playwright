package com.qa.opencart.pages;

import com.microsoft.playwright.*;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class app {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://playwright.dev/");
//            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));


            assertThat(page.locator(".//a[contains(@class,'getStarted_Sjon')]"));
            String title = page.title();
            System.out.println(" title is > "+title);

            browser.close();
            playwright.close();

        }
    }
}