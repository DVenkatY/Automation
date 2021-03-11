import org.testng.annotations.Test;
import pages.CommonPage;
import support.Base;


public class SampleScript extends Base {

    @Test
    public void verifyLinks1(){
        softAssert.assertEquals(CommonPage.isOpenAccountPresent(),"open account present");
        softAssert.assertEquals(CommonPage.isAccountsOverviewPresent(),"accounts overview present");
        softAssert.assertAll();
    }

    @Test
    public void verifyLinks2(){
        softAssert.assertEquals(CommonPage.isFindTransactionsPresent(),"transfer funds present");
        softAssert.assertEquals(CommonPage.isTransferFundsPresent(),"transfer funds present");
        softAssert.assertAll();
    }


    @Test
    public void verifyLinks3(){
        softAssert.assertEquals(CommonPage.isFindTransactionsPresent(),"find transactions present");
        softAssert.assertEquals(CommonPage.isTransferFundsPresent(),"transfer funds present");
        softAssert.assertAll();
    }

}
