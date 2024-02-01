import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.BasicTest;
import pageobject.RoulettePage;

public class RouletteTest extends BasicTest {

    public static final String URL_ROULETTE = "https://csgoempire.com/roulette";
    public static final String NaN = "NaN";
    public static final int NEGATIVE_AMOUNT = -100;
    public static final int VALID_AMOUNT = 100;


    @BeforeMethod
    public void setup() {
        getDriver().get(URL_ROULETTE);
    }

    @Test
    public void testBetInputAndRelatedButtons() {
        RoulettePage roulettePage = new RoulettePage(getDriver());

        roulettePage.fillInBetInput("a" + Keys.TAB);
        softAssert.assertEquals(roulettePage.getBetInputText(), NaN,
                "Expecting NaN when entering letters");

        roulettePage.fillInBetInput("%" + Keys.TAB);
        softAssert.assertEquals(roulettePage.getBetInputText(), NaN,
                "Expecting NaN when entering special characters");

        roulettePage.fillInBetInput(String.valueOf(NEGATIVE_AMOUNT) + Keys.TAB);
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.format("%.2f", (float) NEGATIVE_AMOUNT), "Negative amounts are accepted");

        roulettePage.fillInBetInput(String.valueOf(VALID_AMOUNT) + Keys.TAB);
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.format("%.2f", (float) VALID_AMOUNT), "Valid input is persisted");

        roulettePage.clickAddOneHundrethButton();
        double expectedValue = VALID_AMOUNT + 0.01;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.valueOf(expectedValue), "Bet amount should have been increased with 0.01");

        roulettePage.clickAddOneTenthButton();
        expectedValue += 0.1;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.valueOf(expectedValue), "Bet amount should have been increased with 0.1");

        roulettePage.clickAddOneButton();
        expectedValue += 1;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.valueOf(expectedValue), "Bet amount should have been increased with 1");

        roulettePage.clickAddTenButton();
        expectedValue += 10;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.valueOf(expectedValue), "Bet amount should have been increased with 10");

        roulettePage.clickAddOneHundredButton();
        expectedValue += 100;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.valueOf(expectedValue), "Bet amount should have been increased with 100");

        roulettePage.clickOneHalfButton();
        expectedValue /= 2;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.format("%.2f", (float) expectedValue), "Bet amount should have been halved");

        roulettePage.clickDoubleButton();
        expectedValue *= 2;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.valueOf(expectedValue), "Bet amount should have been doubled");

        roulettePage.clickClearButton();
        expectedValue = 0;
        softAssert.assertEquals(roulettePage.getBetInputText(),
                String.format("%.2f", (float) expectedValue), "Bet amount should have been cleared to 0.00");

        softAssert.assertAll();
    }

    @Test
    public void testPlaceBetButtonsInactiveWhileRouletteSpinning() {
        RoulettePage roulettePage = new RoulettePage(getDriver());

        WebDriverWait w = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        w.until((ExpectedCondition<Boolean>) input -> Double.parseDouble(roulettePage.getCounterText()) > 8);
        softAssert.assertFalse(roulettePage.placeBetButtonsDisabled(),
                "Buttons should not be disabled");

        roulettePage.waitUntilCounterIs0();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        softAssert.assertTrue(roulettePage.placeBetButtonsDisabled(),
                "Buttons should be disabled");
        softAssert.assertAll();

    }

    @Test
    public void testPageLinksAreNotBroken() throws IOException {
        RoulettePage roulettePage = new RoulettePage(getDriver());
        //csgoempiremirror and twitter return 400, the rest of the links can be verified
        List<String> links = roulettePage.getPageLinks().stream()
                .map(e -> e.getAttribute("href"))
                .filter(e -> (!e.contains("csgoempiremirror") && !e.contains("twitter")))
                .toList();

        for (String link : links) {
            URL url = new URL(link);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.connect();
            softAssert.assertEquals(httpURLConnection.getResponseCode(), 200,
                    String.format("Link %s returns unexpected response %s", link, httpURLConnection.getResponseCode()));
        }

        softAssert.assertAll();
    }

    @Test
    public void testPlaceBetButtonWillOpenSignInModalForUsersNotLoggedIn(){
        RoulettePage roulettePage = new RoulettePage(getDriver());

        roulettePage.clickPlaceBetTButton();
        roulettePage.clickSignInModalCancel();

        roulettePage.clickPlaceBetBonusButton();
        roulettePage.clickSignInModalCancel();

        roulettePage.clickPlaceBetCtButton();
        roulettePage.clickSignInModalCancel();

    }
}
