package com.pieceofquality;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.pieceofquality.CustomConditions.minimumSizeOf;
import static com.pieceofquality.CustomConditions.sizeOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

/**
 * Created by piece on 30.06.2016.
 */
public class GoogleSearchSeleniumTest {

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeClass
    public static void setup() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public static void finish() {
        driver.quit();
    }

    @Test
    public void testSearchAndFollowLink() {
        driver.get("http://google.com/ncr");

        search("Selenium automates browsers");

        wait.until(sizeOf(By.cssSelector(searchResults), 10));
        wait.until(textToBePresentInElementLocated(By.cssSelector(searchResults + ":first-child"), "Selenium automates browsers"));

        followNthLink(0);
        wait.until(urlContains("http://www.seleniumhq.org/"));
    }

    @Test
    public void testFollowResultLink() {
        driver.get("http://google.com/ncr");

        search("Selenium automates browsers");

        followNthLink(0);
        wait.until(urlContains("http://www.seleniumhq.org/"));
    }

    public String searchResults = ".srg>.g";

    public void followNthLink(int index) {
        wait.until(minimumSizeOf(By.cssSelector(searchResults), index+1));
        driver.findElements(By.cssSelector(searchResults)).get(index).findElement(By.cssSelector(".r>a")).click();
    }

    public void search(String queryText) {
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys(queryText + Keys.ENTER);
    }
}
