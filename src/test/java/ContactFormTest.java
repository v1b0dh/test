
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactFormTest {

    WebDriver driver;

    // STEP 1: Launch Chrome and open the HTML form
    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        // Load HTML file from the webpage folder
        File file = new File(
            System.getProperty("user.dir"),
            "webpage/form.html"
        );

        if (!file.exists()) {
            throw new RuntimeException(
                "form.html not found at: " + file.getAbsolutePath()
            );
        }

        driver.get(file.getAbsoluteFile().toURI().toString());

        System.out.println("Contact Form Opened Successfully");
    }


    // TEST 1: Verify Name Field
    @Test
    public void verifyNameField() {

        WebElement name =
            driver.findElement(By.id("name"));

        name.sendKeys("Aditya");

        String actualName = name.getAttribute("value");

        Assert.assertEquals(actualName, "Aditya");

        System.out.println("Name Field Test Passed");
    }


    // TEST 2: Verify Email Field
    @Test
    public void verifyEmailField() {

        WebElement email =
            driver.findElement(By.id("email"));

        email.sendKeys("student@gmail.com");

        String actualEmail = email.getAttribute("value");

        Assert.assertEquals(
            actualEmail,
            "student@gmail.com"
        );

        System.out.println("Email Field Test Passed");
    }


    // TEST 3: Verify Message Field
    @Test
    public void verifyMessageField() {

        WebElement message =
            driver.findElement(By.id("message"));

        message.sendKeys("Testing Contact Form");

        String actualMessage =
            message.getAttribute("value");

        Assert.assertEquals(
            actualMessage,
            "Testing Contact Form"
        );

        System.out.println("Message Field Test Passed");
    }


    // TEST 4: Verify Successful Form Submission
    @Test
    public void verifyFormSubmission() {

        driver.findElement(By.id("name"))
              .sendKeys("Aditya");

        driver.findElement(By.id("email"))
              .sendKeys("student@gmail.com");

        driver.findElement(By.id("message"))
              .sendKeys("Testing Contact Form");

        driver.findElement(By.id("submitButton"))
              .click();

        String confirmation =
            driver.findElement(By.id("confirmation"))
                  .getText();

        System.out.println(
            "Confirmation Message: " + confirmation
        );

        Assert.assertEquals(
            confirmation,
            "Form Submitted Successfully!"
        );

        System.out.println("Form Submission Test Passed");
    }


    // TEST 5: Verify Background Color
    @Test
    public void verifyBackgroundColor() {

        String bgColor =
            driver.findElement(By.id("contactBox"))
                  .getCssValue("background-color");

        System.out.println(
            "Background Color: " + bgColor
        );

        Assert.assertEquals(
            bgColor,
            "rgba(173, 216, 230, 1)"
        );

        System.out.println("Background Color Test Passed");
    }


    // STEP 3: Close browser after every test
    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            System.out.println("Browser Closed");
        }
    }

}
