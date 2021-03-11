package support;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.CommonPage;
import pages.LoginPage;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;



public class Base {

    public static Utilities utils=new Utilities();
    public static String currentFilePath=System.getProperty("user.dir")+"\\src\\main\\resources\\";
    public static Map<String,String> configData=new HashMap<String, String>();
    public Logger logger = LogManager.getLogger(Base.class);
    public static WebDriver driver;
    public static HardAssertion hardAssert;
    public static SoftAssertion softAssert;
    public static ExtentReports extent=new ExtentReports();
    public static ExtentSparkReporter spark;
    public static ExtentTest extentTest;


    @BeforeSuite
    public void beforeSuite(){
        try {
            configData = utils.readPropertiesFile(currentFilePath + "config.properties");
            logger.info(String.format("successfully read config properties file beforeSuite"));
            if (configData.get("Browser").equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", currentFilePath+"drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                logger.info(String.format("browser successfully initialized in beforeSuite"));
            }
            spark=new ExtentSparkReporter("spark.html");
            extent.attachReporter(spark);
        }catch(Exception ex){
            logger.error(String.format("error in method '%s' and its description is beforeSuite",ex.getMessage()));
            exit(0);
        }
    }

    @BeforeClass
    public void beforeClass(){
        try{
            String url=configData.get("URL");
            driver.get(url);
            logger.info(String.format("opened %s in beforeClass",url));
            LoginPage.enterUserName();
            LoginPage.enterPassword();
            LoginPage.clickLogin();
        }catch(Exception ex){
            logger.error(String.format("error in method beforeSuite and its description is '%s'",ex.getMessage()));
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        hardAssert=new HardAssertion();
        softAssert=new SoftAssertion();
        extentTest = extent.createTest(method.getName());
        logger.info("beforeMethod test report with given test script started");
    }

    @AfterClass
    public void afterClass(){
        try{
            CommonPage.clickLogout();
            logger.info("Logged out successfully");
            extent.flush();
        }catch(Exception ex){
            logger.error(String.format("error in method afterClass and its description is '%s'",ex.getMessage()));
        }
    }

    @AfterSuite
    public void afterSuite(){
        try {
            driver.quit();
            logger.info("Browser closed successfully");
        }
        catch(Exception ex){
            logger.error(String.format("error in method afterSuite and its description is '%s'",ex.getMessage()));
        }
    }
}
