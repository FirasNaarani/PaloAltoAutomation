package Framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIHandler {
    private final WebDriver driver;
    protected WebDriverWait wait;
    protected static final int DEFAULT_TIMEOUT = 10;

    public GUIHandler(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    public void openBrowser(String url) {
        driver.get(url);
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until((ExpectedCondition<Boolean>) wd ->
        {
            assert wd != null;
            return ((org.openqa.selenium.JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete");
        });
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private List<WebElement> getElements(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public void enterText(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void click(By locator) {
        waitForClickable(locator).click();
    }

    public void switchView(By viewLocator){
        click(viewLocator);
    }

    public void sortByNameAscending(By sortByDropdown) {
        Select sortBySelect = new Select(this.driver.findElement(sortByDropdown));
        sortBySelect.selectByVisibleText("Name (A - Z)");
    }

    public boolean areResultsSortedAlphabetically(By productNames) {
        List<WebElement> products = getElements(productNames);
        List<String> productNameTexts = new ArrayList<>();

        for (WebElement product : products) {
            productNameTexts.add(product.getText());
        }

        List<String> sortedNames = new ArrayList<>(productNameTexts);
        Collections.sort(sortedNames);

        return productNameTexts.equals(sortedNames);
    }

}
