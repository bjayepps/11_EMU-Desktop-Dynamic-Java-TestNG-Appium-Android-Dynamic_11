package com.saucelabs.Tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by llaskin on 04/6/18
 */
public class TestSetup {

    private ThreadLocal<AndroidDriver> driver = new ThreadLocal<AndroidDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * @return the {@link WebDriver} for the current thread
     */
    public AndroidDriver getAndroidDriver() {
        return driver.get();
    }

    /**
     *
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

  /**
   * DataProvider that explicitly sets the browser combinations to be used.
   *
   * @param testMethod
   * @return
   */
  @DataProvider(name = "devices", parallel = true)
  public static Object[][] sauceBrowserDataProvider(Method testMethod) {
      return new Object[][]{
    		  //Uncomment and fix comma to add more emulators
              new Object[]{"Android", "Samsung Galaxy S8 Plus GoogleAPI Emulator", "7.1"},
              new Object[]{"Android", "Samsung Galaxy S7 GoogleAPI Emulator", "7.1"},
              new Object[]{"Android", "Samsung Galaxy S6 GoogleAPI Emulator", "7.1"},  
              new Object[]{"Android", "Samsung Galaxy S7 Edge GoogleAPI Emulator", "7.1"},
              new Object[]{"Android", "Samsung Galaxy S8 GoogleAPI Emulator", "7.1"}

                //Uncomment the below for RDC
//              new Object[]{"Android", "HTC Nexus 9", "7"},
//              new Object[]{"Android", "Google Pixel", "7"},
//              new Object[]{"Android", "LG Nexus 5", "6"},
//              new Object[]{"Android", "Samsung Galaxy S5", "6"}


              

              //Not Needed, we are going to set this for all our Tests via hard coded value
              /*caps.setCapability("appiumVersion", "1.7.2");
                caps.setCapability("deviceOrientation", "portrait");
                caps.setCapability("browserName", "");
                caps.setCapability("app","sauce-storage:test.apk");
              */
      };
  }
  
  protected void createDriver(String platformName, String platformVersion, String deviceName, String methodName) throws MalformedURLException {
  	
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("username", System.getenv("SAUCE_USERNAME"));
      capabilities.setCapability("access_key", System.getenv("SAUCE_ACCESS_KEY"));

      capabilities.setCapability("deviceName", deviceName);
      capabilities.setCapability("platformVersion", platformVersion);
      capabilities.setCapability("platformName", platformName);
      capabilities.setCapability("name",  methodName);
      //Below 4 lines added to make it work on Sauce Labs EmuSim
      capabilities.setCapability("appiumVersion", "1.7.2");
      capabilities.setCapability("deviceOrientation", "portrait");
      capabilities.setCapability("browserName", "");
      capabilities.setCapability("app","sauce-storage:test.apk");


      //Replaced for the Sauce Ondemand URL
      driver.set(new AndroidDriver<WebElement>(
              new URL("https://ondemand.saucelabs.com/wd/hub"),
              capabilities));

      // set current sessionId
      String id = ((AndroidDriver) getAndroidDriver()).getSessionId().toString();
      sessionId.set(id);
  }


    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
    	AndroidDriver driver = getAndroidDriver();
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
    
    /**
     * @return the {@link WebDriver} for the current thread
     */
    public AndroidDriver getWebDriver() {
        return driver.get();
    }
}
