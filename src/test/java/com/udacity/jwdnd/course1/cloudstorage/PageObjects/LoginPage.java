package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  @FindBy(id="inputUsername")
  public WebElement usernameField;

  @FindBy(id="inputPassword")
  public WebElement passwordField;

  @FindBy(id="submit-button")
  public WebElement loginButton;

  public LoginPage(WebDriver webDriver)
  {
    PageFactory.initElements(webDriver,this);
  }

  public void enterUsername(String username)
  {
    usernameField.sendKeys(username);
  }

  public void enterPassword(String password)
  {
    passwordField.sendKeys(password);
  }

  public void login()
  {
    loginButton.click();
  }
}
