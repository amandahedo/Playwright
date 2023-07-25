package com.qa.opencart.pages;

import com.microsoft.playwright.Page;
import com.qa.opencart.pages.Setup.setUp;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;

import static utils.ConfigFile.getConfigDetails;

public class Pages {
    private static Logger Log = Logger.getLogger(setUp.class);
    private Page page = setUp.page;


    @When("^I Login to the application$")
    public void iLoginToTheApplication() throws Exception {
        page.locator("//span[normalize-space()='My Account']").click();
        page.locator("a:text('Login')").click();

        Log.info("\t Login page Title is: " + page.title());

        assert page.isVisible("Forgotten Password") == true;
        Log.info("\t Forgotten Password Link is displayed");


        page.fill("#input-email", getConfigDetails("EMAIl_ADDRESS"));
        Log.info("\t Entered UserName");
        page.fill("#input-password", getConfigDetails("PASSWORD"));
        Log.info("\t Entered password");
        page.locator("input[value='Login']").click();
        Log.info("\t Clicked on Login button");

    }

    @Then("I should be successfully logged in")
    public void iShouldBeSuccessfullyLoggedIn() {
        if (page.isVisible("'Logout' >> visible = true")) {
            Log.info("\t User is successfully logged in");
        }

        Assert.assertEquals(true, page.isVisible("'Logout' >> visible = true"));
        Log.info("\t User is successfully logged in");
    }
}
