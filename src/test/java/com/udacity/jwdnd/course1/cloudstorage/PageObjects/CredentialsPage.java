package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CredentialsPage {

    @FindBy(id="credential-url")
    public WebElement url;

    @FindBy(id="credential-username")
    public WebElement username;

    @FindBy(id="credential-password")
    public WebElement password;

    @FindBy(id="credentialSubmitButton")
    public WebElement submitCredentials;

    @FindBy(xpath="//table[@id='credentialTable']/tbody")
    public WebElement credentialstable;

    @FindBy(id="openCredentialsModal")
    public WebElement addCredentialsButton;

    public CredentialsPage(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
    }
    public void openCredentialsModal()
    {
        addCredentialsButton.click();
    }
    public void enterUrl(String urlString)
    {
        url.sendKeys(urlString);
    }

    public void enterUsername(String usernameString)
    {
        username.sendKeys(usernameString);
    }

    public void enterPassword(String passwordString)
    {
        password.sendKeys(passwordString);
    }

    public void submitCredentials()
    {
        submitCredentials.click();
    }
    public int checkRowsCount()
    {
        List<WebElement> rows= credentialstable.findElements(By.tagName("tr"));
        return rows.size();
    }

    public List<WebElement> getCredentialsRows()
    {
        return credentialstable.findElements(By.tagName("tr"));
    }

    public void clearUrl()
    {
        url.clear();
    }
}
