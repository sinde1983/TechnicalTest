package com.identity.test.page_objects;

import com.identity.test.helpers.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;


public class ResultsPage extends BasePage {

    public Map<String,String> getResults() {
        Map<String, String> output = new HashMap<>();
        String locator = "//div[3]/div[1]/div//div[@class='jsx-3058503346']/dl[%d]";
        for (int i = 1; i < 5; i++) {
            //waitForElementToBeDisplay(BasePage.driver.findElement(By.xpath("//div[3]/div[1]/div//div[@class='jsx-3058503346']/dl[1]")));
            WebElement element = BasePage.driver.findElement(By.xpath(String.format(locator, i)));
            output.put(element.findElement(By.tagName("dt")).getText().toUpperCase(), element.findElement(By.tagName("dd")).getText());
        } return output;
    }
}
