package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.CredentialsPage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.NotesPage;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private RegistrationPage registrationPage;
	private LoginPage loginPage;
	private NotesPage notesPage;
	private CredentialsPage credentialsPage;
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		registrationPage=new RegistrationPage(driver);
		loginPage=new LoginPage(driver);
		notesPage=new NotesPage(driver);
		credentialsPage=new CredentialsPage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void signupAndLogin()
	{
		driver.get("http://localhost:" + this.port + "/signup");
		registrationPage.enterFirstname("Ghansham");
		registrationPage.enterLastname("Ghate");
		registrationPage.enterUsername("admin");
		registrationPage.enterPassword("12345");
		registrationPage.clickSubmitButton();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.enterUsername("admin");
		loginPage.enterPassword("12345");
		loginPage.login();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Assertions.assertEquals("Home", driver.getTitle());
	}
	@Test
	public void unAuthorizedUser()
	{
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.enterUsername("admin");
		loginPage.enterPassword("123456");
		loginPage.login();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void AddNote()
	{
		login();
		driver.get("http://localhost:" + this.port + "/Note");
		int intialRows=notesPage.checkRowsCount();
		addNoteFunction();
        int finalRows=notesPage.checkRowsCount();
        Assertions.assertEquals(intialRows+1,finalRows);
	}
    @Test
	public void deleteNote()
	{
		login();
		driver.get("http://localhost:" + this.port + "/Note");
		addNoteFunction();
		driver.get("http://localhost:" + this.port + "/Note");
		WebElement lastRow =notesPage.getNotesRows().get(1);
		List<WebElement> columns=lastRow.findElements(By.tagName("td"));
		WebElement deleteButton=columns.get(0).findElement(By.tagName("a"));
		System.out.println("delete "+deleteButton.getText());
		int intialRows=notesPage.checkRowsCount();
		deleteButton.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		int finalRows=notesPage.checkRowsCount();

		Assertions.assertEquals(intialRows,finalRows+1);
	}
    @Test
	public void editNote()
	{
		login();
		driver.get("http://localhost:" + this.port + "/Note");
		addNoteFunction();
		driver.get("http://localhost:" + this.port + "/Note");
		WebElement lastRow =notesPage.getNotesRows().get(1);
		List<WebElement> columns=lastRow.findElements(By.tagName("td"));
		WebElement editButton=columns.get(0).findElement(By.tagName("button"));
		editButton.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		notesPage.clearNoteTitle();
		notesPage.enterNoteTitle("new title");
		notesPage.clickSubmitNote();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement editedRow =notesPage.getNotesRows().get(1);
		WebElement titleColumn=editedRow.findElement(By.tagName("th"));
		String noteTitle=titleColumn.getText();
		System.out.println("noteTitle "+noteTitle);
		Assertions.assertEquals("new title",noteTitle);
	}

	@Test
	public void AddCredential()
	{
		login();
		driver.get("http://localhost:" + this.port + "/Credentials");
		int intialRows=credentialsPage.checkRowsCount();
		addCredentialsFunction();
		int finalRows=credentialsPage.checkRowsCount();
		Assertions.assertEquals(intialRows+1,finalRows);
	}
	@Test
	public void EditCredential()
	{
		login();
		driver.get("http://localhost:" + this.port + "/Credentials");
		addCredentialsFunction();
		driver.get("http://localhost:" + this.port + "/Credentials");
		WebElement lastRow =credentialsPage.getCredentialsRows().get(0);
		List<WebElement> columns=lastRow.findElements(By.tagName("td"));
		WebElement editButton=columns.get(0).findElement(By.tagName("button"));
		editButton.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		credentialsPage.clearUrl();
		credentialsPage.enterUrl("new url");
		credentialsPage.submitCredentials();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement editedRow =credentialsPage.getCredentialsRows().get(0);
		WebElement UrlColumn=editedRow.findElement(By.tagName("th"));
		String urlColumnText=UrlColumn.getText();
		System.out.println("url "+urlColumnText+" "+credentialsPage.checkRowsCount());
		Assertions.assertEquals("new url",urlColumnText);
	}
	@Test
	public void deleteCredentials()
	{
		login();
		driver.get("http://localhost:" + this.port + "/Credentials");
		addCredentialsFunction();
		driver.get("http://localhost:" + this.port + "/Credentials");
		WebElement lastRow =credentialsPage.getCredentialsRows().get(0);
		List<WebElement> columns=lastRow.findElements(By.tagName("td"));
		WebElement deleteButton=columns.get(0).findElement(By.tagName("a"));
		System.out.println("delete "+deleteButton.getText());
		int intialRows=credentialsPage.checkRowsCount();
		deleteButton.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		int finalRows=credentialsPage.checkRowsCount();

		Assertions.assertEquals(intialRows,finalRows+1);
	}
	public void login()
	{
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.enterUsername("admin");
		loginPage.enterPassword("12345");
		loginPage.login();
	}
	public void addNoteFunction()
	{
		notesPage.clickAddNoteButton();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		notesPage.enterNoteTitle("Sample");
		notesPage.enterNoteDescription("This is a sample description");
		notesPage.clickSubmitNote();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void addCredentialsFunction()
	{
        credentialsPage.openCredentialsModal();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		credentialsPage.enterUrl("http://localhost:8080");
		credentialsPage.enterUsername("admin");
		credentialsPage.enterPassword("12345");
		credentialsPage.submitCredentials();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
}
