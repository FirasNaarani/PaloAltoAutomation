package Tests;

import Framework.BaseTestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EX1_Test extends BaseTestCase {
    String url;
    String searchId;
    String searchText;
    String searchButtonId;
    String listViewId;
    String gridViewId;
    String sortDropDownId;
    String productNamesId;

    By searchInputLocator;
    By searchButtonLocator;
    By listViewLocator;
    By gridViewLocator;
    By sortDropDownLocator;
    By productNamesLocator;

    WebElement searchInput;


    //Constructor
    public EX1_Test() {
        super(getChromeDriverWithMaximizedWindow());
    }

    private static WebDriver getChromeDriverWithMaximizedWindow() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private Boolean areParametersEmpty(String url) {
        // Check if the parameter is null or empty
        return url == null || url.trim().isEmpty();
    }

    @BeforeClass
    public void SetUp() {
        // ENV Params
        this.url = xmlReader.getValueByName("URL");
        this.searchId = xmlReader.getValueByName("SEARCH_XPATH");
        this.searchText = xmlReader.getValueByName("SEARCH_TEXT");
        this.searchButtonId = xmlReader.getValueByName("SEARCH_BUTTON");
        this.listViewId = xmlReader.getValueByName("LIST_VIEW_XPATH");
        this.gridViewId = xmlReader.getValueByName("GRID_VIEW_XPATH");
        this.sortDropDownId = xmlReader.getValueByName("SORT_BY_DROPDOWN");
        this.productNamesId = xmlReader.getValueByName("PRODUCT_NAMES_CSS");

        //Check ENV Params
        Boolean isEmpty = areParametersEmpty(url);
        if (isEmpty)
        {
            Assert.fail("ONE OF THE PARAMS IS NULL OR EMPTY, PLEASE CHECK 'Resources.xml'");
        } else
        {
            try
            {
                //Open BROWSER
                guiHandler.openBrowser(url);

            } catch (Exception e)
            {
                System.out.println("ERROR WILL OPENING BROWSER");
                e.printStackTrace();
            }
        }
    }

    @Test
    public void EX1(){
        try {
            this.searchInputLocator = By.xpath(this.searchId);
            this.searchInput = guiHandler.waitForVisibility(searchInputLocator);
            if (this.searchInput == null)
            {
                Assert.fail("SEARCH NOT FOUND");
            }
            else{

                System.out.println("\tINIT\t-\tSTART");
                this.searchButtonLocator = By.cssSelector(this.searchButtonId);
                this.listViewLocator = By.xpath(this.listViewId);
                this.gridViewLocator = By.xpath(this.gridViewId);
                this.sortDropDownLocator = By.cssSelector(this.sortDropDownId);
                this.productNamesLocator = By.cssSelector(this.productNamesId);
                System.out.println("\tINIT\t-\tEND");
            }

            guiHandler.enterText(this.searchInputLocator,this.searchText);
            guiHandler.click(this.searchButtonLocator);

            guiHandler.switchView(this.listViewLocator);
            guiHandler.sortByNameAscending(this.sortDropDownLocator);

            boolean check = guiHandler.areResultsSortedAlphabetically(this.productNamesLocator);
            if(check)
                System.out.println("\tAlphabetical Sort Test\t-\tPASS");
            else
                System.out.println("\tAlphabetical Sort Test\t-\tFAILED");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void EX2(){
        EX1();
        //To Be Continued...
    }
}
