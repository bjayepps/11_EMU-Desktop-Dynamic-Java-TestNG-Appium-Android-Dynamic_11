package com.saucelabs.Tests;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

/**
 * Created by llaskin on 4/6/18.
 */
public class LongTest extends TestSetup{
    @Test(dataProvider = "devices")
    public void longTest(String platformName, String deviceName, String platformVersion, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {

        this.createDriver(platformName, platformVersion, deviceName, method.getName());
        AndroidDriver driver = this.getAndroidDriver();


        /* Get the elements. */
        MobileElement buttonTwo = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/digit2")));
        MobileElement buttonPlus = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/plus")));
        MobileElement buttonEquals = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/equal")));
        MobileElement resultField = (MobileElement) (driver.findElement(By.xpath("//android.widget.EditText[1]")));

        MobileElement buttonFour = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/digit4")));
        MobileElement buttonSix = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/digit6")));




        /* Add two and two. */
        buttonTwo.click();
        buttonPlus.click();
        buttonTwo.click();
        driver.getScreenshotAs(OutputType.FILE);
        buttonEquals.click();
        driver.getScreenshotAs(OutputType.FILE);
        /* Check if within given time the correct result appears in the designated field. */
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, "4"));

        buttonPlus.click();
        buttonFour.click();
        buttonPlus.click();
        buttonFour.click();
        driver.getScreenshotAs(OutputType.FILE);
        buttonEquals.click();
        driver.getScreenshotAs(OutputType.FILE);
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, "12"));

        buttonPlus.click();
        buttonTwo.click();
        buttonPlus.click();
        buttonFour.click();
        driver.getScreenshotAs(OutputType.FILE);
        buttonEquals.click();
        driver.getScreenshotAs(OutputType.FILE);
        /* Check if within given time the correct result appears in the designated field. */
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, "18"));

        buttonPlus.click();
        buttonSix.click();
        buttonPlus.click();
        buttonFour.click();
        driver.getScreenshotAs(OutputType.FILE);
        buttonEquals.click();
        driver.getScreenshotAs(OutputType.FILE);
        /* Check if within given time the correct result appears in the designated field. */
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, "28"));
    }
}
