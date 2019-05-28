package com.dvinnik.cucumber;

import com.dvinnik.cucumber.utils.Constants;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValidTodoTest {

    private WebDriver driver;
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", Constants.DRIVER_PATH);
        driver = new ChromeDriver();
        driver.get(Constants.BASE_URL);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("todo name is {string}")
    public void todoNameIs(final String todoName) throws Throwable {
        findNewTodoItem().sendKeys(todoName);
    }

    @When("I press {string}")
    public void iPress(final String operation) throws Throwable {
        findNewTodoItem().sendKeys(Keys.valueOf(operation));
    }

    @Then("todo item with the name {string} is created")
    public void todoItemWithTheNameIsCreated(final String expectedTodoName) throws Throwable {
        findTodoItemByName(expectedTodoName);
    }

    private WebElement findTodoItemByName(final String searchedTodoName) {
        return new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT_SEC).until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("todo-list"))).stream()
        .filter(todoItem -> searchedTodoName.equals(todoItem.getText()))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Could not find element with the name requested"));
    }

    private WebElement findNewTodoItem() {
        return new WebDriverWait(driver, Constants.ELEMENT_TIMEOUT_SEC).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".new-todo")));
    }
}
