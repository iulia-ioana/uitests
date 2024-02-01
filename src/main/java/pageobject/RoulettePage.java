package pageobject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RoulettePage {
    public static final String DISABLE_ATTRIBUTE = "disable";
    private final WebDriver driver;

    public RoulettePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUntilPageLoads();
    }

    @FindBy(css = "input[data-v-83de194b]")
    WebElement enterBetAmountInput;

    @FindBy(css = "div[data-v-fcee0e9a].text-2xl")
    public
    WebElement counter;

    @FindBy(css = "button[data-v-1c20d188]")
    List<WebElement> placeBetButtons;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'Clear')]")
    WebElement clearButton;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'+ 0.01')]")
    WebElement addOneHundrethButton;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'+ 0.1')]")
    WebElement addOneTenthButton;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'+ 1')]")
    WebElement addOneButton;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'+ 10')]")
    WebElement addTenButton;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'+ 100')]")
    WebElement addOneHundredButton;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'1/ 2')]")
    WebElement oneHalfButton;

    @FindBy(xpath = "//button[contains(@class,'bet-input__control') and contains(text(),'x 2')]")
    WebElement doubleButton;

    @FindBy(css = "a")
    List<WebElement> links;

    @FindBy(xpath = "//button[.//img[@alt='ct']]")
    WebElement placeBetCtButton;

    @FindBy(xpath = "//button[.//img[@alt='bonus']]")
    WebElement placeBetBonusButton;

    @FindBy(xpath = "//button[.//img[@alt='t']]")
    WebElement placeBetTButton;

    @FindBy(css = "button[data-v-43c3e565]")
    WebElement signInModalCancel;


    public void fillInBetInput(String bet) {
        enterBetAmountInput.sendKeys(Keys.CONTROL + "A");
        enterBetAmountInput.sendKeys(Keys.DELETE);
        enterBetAmountInput.sendKeys(bet);
    }

    public String getBetInputText() {
        return enterBetAmountInput.getAttribute("value");
    }

    public void clickClearButton() {
        clearButton.click();
    }

    public void clickAddOneHundrethButton() {
        addOneHundrethButton.click();
    }

    public void clickAddOneTenthButton() {
        addOneTenthButton.click();
    }

    public void clickAddOneButton() {
        addOneButton.click();
    }

    public void clickAddTenButton() {
        addTenButton.click();
    }

    public void clickAddOneHundredButton() {
        addOneHundredButton.click();
    }

    public void clickOneHalfButton() {
        oneHalfButton.click();
    }

    public void clickDoubleButton() {
        doubleButton.click();
    }

    public List<WebElement> getPageLinks() {
        return links;
    }

    public void waitUntilPageLoads() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(doubleButton));
    }

    public String getCounterText() {
        return counter.getAttribute("innerText");
    }

    public void waitUntilCounterIs0() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(35));
        w.until(ExpectedConditions.attributeToBe(counter, "innerText", "0.00"));
    }

    public boolean placeBetButtonsDisabled() {
        boolean disabled = true;
        for (WebElement button : placeBetButtons) {
            disabled &= Boolean.parseBoolean(button.getAttribute(DISABLE_ATTRIBUTE));
        }
        return disabled;
    }

    public void waitUntilButtonIsEnabled(WebElement element){
        new WebDriverWait(driver, Duration.ofSeconds(9))
                .until(ExpectedConditions.attributeToBe(element, DISABLE_ATTRIBUTE, "false"));
    }

    public void clickPlaceBetBonusButton() {
        if (Boolean.parseBoolean(placeBetBonusButton.getAttribute(DISABLE_ATTRIBUTE))) {
            waitUntilButtonIsEnabled(placeBetBonusButton);
        }
        placeBetBonusButton.click();
    }

    public void clickPlaceBetCtButton() {
        if (Boolean.parseBoolean(placeBetCtButton.getAttribute(DISABLE_ATTRIBUTE))) {
            waitUntilButtonIsEnabled(placeBetCtButton);
        }
        placeBetCtButton.click();
    }

    public void clickPlaceBetTButton() {
        if (Boolean.parseBoolean(placeBetTButton.getAttribute(DISABLE_ATTRIBUTE))) {
            waitUntilButtonIsEnabled(placeBetTButton);
        }
        placeBetTButton.click();
    }

    public void clickSignInModalCancel(){
        signInModalCancel.click();
    }
}
