import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class AutomationExercise {
    public void runToAutomationExercise() {
        initializedDriver();
        WebDriver driver = new ChromeDriver();

        try {
            windowMaximize(driver);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            navigateToSite(driver);

            performLogin(driver, "Test@tester.com", "123456");
            advert(driver, "//a[@href='#Men']");
            advert(driver, "//a[@href='/product_details/43']");

            checkout(driver, "5");
            advert(driver, "//a[@href='/payment']");
            Actions(driver, "//input[@name='name_on_card']", "Test Tester", "1234567891011121", "123", "12", "1234");
            Click(driver, "//button[@data-qa='pay-button']");
            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // WebDriver'ı kapatmayı unutmayın
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void initializedDriver() {
        WebDriverManager.chromedriver();
        WebDriverManager.chromedriver().setup();
    }

    private void windowMaximize(WebDriver driver) {
        driver.manage().window().maximize();
    }

    private void navigateToSite(WebDriver driver) {
        driver.get("https://automationexercise.com/");
    }

    private void Actions(WebDriver driver, String xpath, String... keysToSend) {
        try {
            Actions actions = new Actions(driver);
            WebElement inputElement = driver.findElement(By.xpath(xpath));

            actions.click(inputElement);
            for (String keys : keysToSend) {
                actions.sendKeys(keys).sendKeys(Keys.TAB);
            }
            actions.perform();
        } catch (Exception e) {
            Exception exception = e;
        }
    }

    private void Click(WebDriver driver, String xpath) {
        try {
            Actions actions = new Actions(driver);
            WebElement inputElement = driver.findElement(By.xpath(xpath));
            actions.click(inputElement);
            actions.perform();
        } catch (Exception e) {
            Exception exception = e;
        }
    }

    private   void performLogin(WebDriver driver, String email, String password) {
        Click(driver, "//a[@href='/login']");
        Actions(driver, "//input[@data-qa='login-email']", email, password);
        Click(driver, "//button[@data-qa='login-button']");
    }

    private void checkout(WebDriver driver, String quantity) {
        WebElement amount = driver.findElement(By.id("quantity"));
        amount.sendKeys(Keys.CONTROL + "a");
        amount.sendKeys(Keys.DELETE);
        amount.sendKeys(quantity);
        Click(driver, "//button[@class='btn btn-default cart']");
        Click(driver, "//a[@href='/view_cart']");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Click(driver, "//a[@class='btn btn-default check_out']");
    }

    private void advert(WebDriver driver, String path) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(path));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }
}