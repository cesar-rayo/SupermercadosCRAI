package crai.automation.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import crai.automation.commons.CraiCommons;

public class CraiUI {
	
	WebDriver driver;
	JavascriptExecutor jse;
	String craiURL = "https://luzavargas.github.io/supermercado/";
	
	@BeforeMethod(groups={"navigation","products"})
	public void createChromeDriver(){
		System.setProperty("webdriver.chrome.driver",
				"/media/cesar-rayo/948688348688193E/java/ubuntu/selenium/webdrivers/chromedriver_linux64/chromedriver");
		driver = new ChromeDriver();
		CraiCommons.cleanBrowser(driver);
	}
	
	@AfterMethod(groups={"navigation","products"})
	public void closeDriver(){
		driver.close();
	}
	
	@Test(groups={"navigation"})
	public void loadHomePage() {
		driver.get(craiURL);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='slider']/div[1]")));
		CraiCommons.takePrintScreen(driver, "loadHomePage.png");
		Assert.assertNotNull(element);
		
	}
	
	@Test(groups={"navigation"})
	public void scrollTillLavaplatos() {
		driver.get(craiURL);
		WebElement element = driver.findElement(By.xpath("//*[@id='content']/div[8]/h3"));
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", element);
		CraiCommons.takePrintScreen(driver, "scrollTillLavaplatos.png");
		String expected = "Lavaplatos 500gr";
		Assert.assertEquals(element.getText(), expected);
	}
	
	@Test(groups={"navigation"})
	public void searchProduct(){
		driver.get(craiURL);
		WebElement txtSearch = driver.findElement(By.xpath("//*[@id='keyword']"));
		Actions builder = new Actions(driver);
		Action writeText = builder.click(txtSearch)
				.keyDown(Keys.SHIFT)
				.sendKeys("q")
				.keyUp(Keys.SHIFT)
				.sendKeys("ueso")
				.doubleClick(txtSearch)
				.build();
		writeText.perform();
		CraiCommons.takePrintScreen(driver, "searchProduct_1.png");
		driver.findElement(By.xpath("//*[@id='searchbutton']")).click();;
		CraiCommons.takePrintScreen(driver, "searchProduct_2.png");
		String currentUrl = driver.getCurrentUrl();
		currentUrl.contains("Queso");
		Assert.assertTrue(currentUrl.contains("Queso"));
	}
	
	@Test(groups={"products"})
	public void navigateToProductos(){
		driver.navigate().to(craiURL);
		SoftAssert softAssertion = new SoftAssert();
		driver.findElement(By.linkText("Productos")).click();
		WebElement producto = driver.findElement(By.xpath("//*[@id='content']/div[1]/h3"));
		String expectedProduct = "Uva Importada Kilo";
		CraiCommons.takePrintScreen(driver, "navigateToProductos_1.png");
		softAssertion.assertEquals(producto.getText(), expectedProduct);
		driver.navigate().back();
		softAssertion.assertEquals(driver.getCurrentUrl(), craiURL);
		CraiCommons.takePrintScreen(driver, "navigateToProductos_2.png");
		softAssertion.assertAll();
	}
	
	@Test(groups={"products"})
	public void navigateToProductosAseo(){
		driver.navigate().to(craiURL);
		
		WebElement productos = driver.findElement(By.xpath("//*[@id='top_nav']/ul/li[2]/a"));
		WebElement aseo = driver.findElement(By.xpath("//*[@id='top_nav']/ul/li[2]/ul/li[1]/a"));
		
		Actions builder = new Actions(driver);
		Action act = builder.moveToElement(productos)
				.moveToElement(aseo)
				.click(aseo)
				.build();
		act.perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.urlContains("aseo"));
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("aseo"));
	}
	
	@Test(groups={"products"})
	public void navigateToProductosBebidas(){
		driver.navigate().to(craiURL);
		SoftAssert softAssertion = new SoftAssert();
		WebElement productos = driver.findElement(By.xpath("//*[@id='top_nav']/ul/li[2]/a"));
		WebElement bebidas = driver.findElement(By.xpath("//*[@id='top_nav']/ul/li[2]/ul/li[2]/a"));
		
		Actions builder = new Actions(driver);
		Action act = builder.moveToElement(productos)
				.moveToElement(bebidas)
				.click(bebidas)
				.build();
		act.perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.urlContains("bebidas"));
		String currentUrl = driver.getCurrentUrl();
		softAssertion.assertTrue(currentUrl.contains("bebidas"));
		driver.navigate().back();
		softAssertion.assertEquals(driver.getCurrentUrl(), craiURL);
		softAssertion.assertAll();
	}
}
