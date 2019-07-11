package crai.automation.commons;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class CraiCommons {

	public static void takePrintScreen(WebDriver driver, String fileName) {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File screenShot = takeScreenShot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShot,
					new File("/home/cesar-rayo/Desktop/workspace/SupermercadosCRAI/" + "scrrenShots/" + fileName));
		} catch (Exception e) {
			System.out.println("Could not save Screenshot '" + fileName + "'");
		}
	}
	
	public static void cleanBrowser(WebDriver driver){
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

}
