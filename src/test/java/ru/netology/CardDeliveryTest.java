package ru.netology;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    private WebDriver driver;

    public String shouldSetDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, 5);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(currentDate.getTime());
    }

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpTest() {
//        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitOrder() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Майкоп");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(shouldSetDate());
        $("[name='name']").setValue("Иванов Петр");
        $("[name='phone']").setValue("+71231234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(16));
    }
}
