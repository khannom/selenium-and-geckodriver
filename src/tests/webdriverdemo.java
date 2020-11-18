package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class webdriverdemo {

	static WebDriver driver;

	@BeforeClass
	public static void preClass() {

		System.setProperty("webdriver.gecko.driver",
				"/home/diego/Documents/software/geckodriver-v0.28.0-linux64/geckodriver");
		driver = new FirefoxDriver();
		// Puts an Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Launch website
		driver.navigate().to("http://www.calculator.net/");

		// Maximize the browser
		driver.manage().window().maximize();

		// Click on Math Calculators
		driver.findElement(By.xpath("//*[@id=\"contentout\"]/table/tbody/tr/td[3]/div[2]/a")).click();

		// Click on Percent Calculators
		driver.findElement(By.xpath("//*[@id=\"content\"]/table[2]/tbody/tr/td/div[3]/a")).click();
	}

	@AfterClass
	public static void postClass() {
		driver.close();
	}

	@After
	public void tearDown() {
		driver.findElement(By.id("cpar1")).clear();
		driver.findElement(By.id("cpar2")).clear();
	}

	@Test
	public void percentageTestPositiveInteger() {
		// Test 1: Integer positive values
		// Enter value 10 in the first number of the percent Calculator
		driver.findElement(By.id("cpar1")).sendKeys("10");

		// Enter value 50 in the second number of the percent Calculator
		driver.findElement(By.id("cpar2")).sendKeys("50");

		// Click Calculate Button
		driver.findElement(By.xpath("//*[@id=\"content\"]/table[1]/tbody/tr[2]/td/input[2]")).click();

		// Get the Result Text based on its xpath
		String result = driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/font/b")).getText();

		assertEquals(result, "5");
	}

	@Test
	public void percentageTestNegativeInteger() {
		// Test 2: Integer negative and positive values
		driver.findElement(By.id("cpar1")).sendKeys("-20");
		driver.findElement(By.id("cpar2")).sendKeys("100");

		driver.findElement(By.xpath("//*[@id=\"content\"]/table[1]/tbody/tr[2]/td/input[2]")).click();

		String result = driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/font/b")).getText();

		assertEquals(result, "-20");
	}

	@Test
	public void percentageTestNegativeFloat() {
		// Test 3: Real values
		driver.findElement(By.id("cpar1")).sendKeys("5.5");
		driver.findElement(By.id("cpar2")).sendKeys("-100");

		driver.findElement(By.xpath("//*[@id=\"content\"]/table[1]/tbody/tr[2]/td/input[2]")).click();

		String result = driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/font/b")).getText();

		assertEquals(result, "-5.5");
	}

	@Test
	public void percentageTestInvalidInput() {
		// Test 4: Invalid input
		driver.findElement(By.id("cpar1")).sendKeys("hellow");
		driver.findElement(By.id("cpar2")).sendKeys("world");

		driver.findElement(By.xpath("//*[@id=\"content\"]/table[1]/tbody/tr[2]/td/input[2]")).click();

		String result = driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/font")).getText();

		assertEquals(result, "Please provide two numeric values in any fields below.");
	}
}