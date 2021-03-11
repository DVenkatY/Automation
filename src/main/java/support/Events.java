package support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Events extends Base{
    public static Logger logger = LogManager.getLogger(Events.class);

    public static void setText(String locator, String text, String elementName){
        try{
            WebElement ele = driver.findElement(By.xpath(locator));
            ele.sendKeys(text);
            logger.info("Entered text in "+elementName);
        }catch(Exception ex){
            logger.error(String.format("error in enter text in '%s' and error description is '%s'",elementName,ex.getMessage()));
        }
    }


    public static void click(String locator, String elementName){
        try{
            WebElement ele = driver.findElement(By.xpath(locator));
            ele.click();
            logger.info("Clicked on "+elementName);
        }catch(Exception ex){
            logger.error(String.format("error while clicking on '%s' and error description is '%s'",elementName,ex.getMessage()));
        }
    }


    public static String isElementPresent(String locator, String elementName){
        try{
            WebElement ele = driver.findElement(By.xpath(locator));
            logger.info(elementName+" is present");
            if(ele.isDisplayed())
                return "present";
        }
        catch(Exception ex){
            logger.error(String.format("error while verifying presence of '%s' and error description is '%s'",elementName,ex));
            return "element error, refer QA.log for details";
        }
        logger.info(elementName+" not present");
        return "not present";
    }

    public static String getScreenshot() {
        String screenshotPath="";
        try {
            String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            screenshotPath = System.getProperty("user.dir") + "\\target\\" + time + ".png";
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File(screenshotPath));
        }catch(Exception ex){
            logger.error(ex.getMessage());

        }
        return screenshotPath;
    }

}
