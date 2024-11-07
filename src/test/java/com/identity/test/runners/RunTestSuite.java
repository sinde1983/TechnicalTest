package com.identity.test.runners;


import com.identity.test.helpers.BasePage;
import com.identity.test.helpers.InputFileReader;
import com.identity.test.helpers.OutputFileReader;
import com.identity.test.helpers.TestContext;
import com.identity.test.page_objects.FreeCarCheckPage;
import com.identity.test.page_objects.ResultsPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class RunTestSuite extends BasePage {

    private FreeCarCheckPage freeCarCheckPage = PageFactory.initElements(BasePage.driver,FreeCarCheckPage.class);
    private static BasePage driver = new BasePage();
    InputFileReader inputFileReader = new InputFileReader();
    TestContext testContext = TestContext.getInstance();
    ResultsPage resultsPage = new ResultsPage();
    OutputFileReader outputFileReader = new OutputFileReader();

    @Test
    public void testRunner() throws IOException {
        List<String> regNumbers = inputFileReader.readInputFile();
        Map<String,String> actualResults= new HashMap<>();
        List<Map<String,String>> expectedResults = outputFileReader.readOutputFile();
        Map<String,String> temp = new HashMap<>();

        for(String num: regNumbers){
            getDriver().navigate().to(testContext.getProperty("base.url"));
            freeCarCheckPage.searchRegistrationNumber(num);
            freeCarCheckPage.clickFreeCarCheckButton();
            timeUnitWait(15);
            waitForPage();
            if(isAlertPresent()){
                String alertText = BasePage.driver.switchTo().alert().getText();
                System.out.println("Car details not found for" +num);
                BasePage.driver.switchTo().alert().accept();
            }else {
                actualResults = resultsPage.getResults();
                System.out.println("Actual Output -------->" +actualResults);
                System.out.println("Expected Result Size -------->" +expectedResults.size());
                for (Map<String, String> expectedResult : expectedResults) {
                    if (expectedResult.containsValue(num)) {
                        temp = expectedResult;
                        System.out.println("Temp -------->" + temp);
                    }
                }
                assertThat(actualResults.entrySet(), both(everyItem(isIn(temp.entrySet()))).and(containsInAnyOrder(temp.entrySet().toArray())));
                //assertThat(actualResults.entrySet(), containsInAnyOrder(temp.entrySet().toArray()));
                }
            }
        }


    @BeforeClass
    public static void setupDriver(){
        driver.initialiseDriver();
    }

   @AfterClass
    public static void destroyDriver(){
       if(BasePage.driver != null){
           BasePage.driver.quit();
           BasePage.driver = null;
       }
    }

}
