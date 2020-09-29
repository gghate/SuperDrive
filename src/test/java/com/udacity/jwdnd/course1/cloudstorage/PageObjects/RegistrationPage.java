package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {

    @FindBy(id="inputFirstName")
    public WebElement firstname;
    @FindBy(id="inputLastName")
    public WebElement lastname;
    @FindBy(id="inputUsername")
    public WebElement username;
    @FindBy(id="inputPassword")
    public WebElement password;
    @FindBy(id="submit-button")
    public WebElement submitButton;
    @FindBy(id="login-link")
    public WebElement loginLink;

    @FindBy(id="success-msg")
    public  WebElement successMsg;

    @FindBy(id="error-msg")
    public WebElement errorMsg;

    public  RegistrationPage(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
    }

    public void enterFirstname(String Firstname)
    {
        firstname.sendKeys(Firstname);
    }
    public void enterLastname(String Lastname)
    {
        lastname.sendKeys(Lastname);
    }
    public void enterUsername(String Username)
    {
        username.sendKeys(Username);
    }
    public void enterPassword(String Password)
    {
        password.sendKeys(Password);
    }
    public void clickSubmitButton()
    {
        submitButton.click();
    }

    public void clickLoginLink()
    {
        loginLink.click();
    }

    public String getSuccessMsg()
    {
        return successMsg.getText();
    }

    public String getErrorMsg()
    {
        return errorMsg.getText();
    }
}
