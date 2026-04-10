package stepdefinitions;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import utils.*;

public class Hooks {

    Logger log = LoggerUtil.getLogger(Hooks.class);

    @Before
    public void setup() {

        log.info("===== Starting Test Execution =====");

        DriverFactory.initDriver();
        WebDriver driver = DriverFactory.getDriver();

        log.info("Opening URL: " + ConfigReader.get("url"));
        driver.get(ConfigReader.get("url"));

        AdsHandler.removeAds(driver);
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {

            log.error("❌ Test FAILED: " + scenario.getName());

            String screenshotPath = ScreenshotUtils.capture(scenario.getName());

            log.error("📸 Screenshot saved at: " + screenshotPath);

        } else {
            log.info("✅ Test PASSED: " + scenario.getName());
        }

        DriverFactory.quitDriver();

        log.info("Closing browser");
        log.info("===== Test Execution Finished =====\n");
    }
}