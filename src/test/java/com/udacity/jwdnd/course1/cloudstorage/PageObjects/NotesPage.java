package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NotesPage {

    @FindBy(id="note-title")
    public WebElement notetitle;
    @FindBy(id ="note-description")
    public WebElement notedescription;
    @FindBy(id="submitModal")
    public WebElement submitnote;
    @FindBy(xpath="//table[@id='userTable']/tbody")
    public WebElement notetable;
    @FindBy(id="addNoteButton")
    public WebElement addNote;

    public NotesPage(WebDriver webDriver)
    {
        PageFactory.initElements(webDriver,this);
    }

    public void clickAddNoteButton()
    {
        addNote.click();
    }

    public void enterNoteTitle(String title)
    {
        notetitle.sendKeys(title);
    }
    public void enterNoteDescription(String description)
    {
        notedescription.sendKeys(description);
    }

    public void clickSubmitNote()
    {
        submitnote.click();
    }
    public int checkRowsCount()
    {
       List<WebElement>rows= notetable.findElements(By.tagName("tr"));
       return rows.size();
    }

    public List<WebElement> getNotesRows()
    {
        return notetable.findElements(By.tagName("tr"));
    }

    public void clearNoteTitle()
    {
        notetitle.clear();
    }
}
