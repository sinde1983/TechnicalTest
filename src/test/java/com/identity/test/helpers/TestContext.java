package com.identity.test.helpers;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestContext {
    private Properties properties;

    InputStream inputStream = null;

    private static TestContext loader = new TestContext();

    private String browser = StringUtils.isEmpty(System.getProperty("browser"))?"chrome":System.getProperty("browser");

    public TestContext(){
        loadProperties(browser);
    }

    public void loadProperties(String browser) {
        properties = new Properties();
        try {
            inputStream = new FileInputStream("src/test/resources/test.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with reading property file");
        }

        if(StringUtils.isEmpty(browser)) {
            setBrowserType();
        }
    }

    private void setBrowserType() {
        browser = getProperty("browser");
    }

    public String getBrowserType() {
        return browser;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static TestContext getInstance() {
        return loader;
    }
}
