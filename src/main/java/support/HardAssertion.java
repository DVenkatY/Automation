package support;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import java.io.IOException;

import static support.Base.extentTest;

public class HardAssertion extends Assertion {
    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        extentTest.pass(assertCommand.getExpected().toString());
    }

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        String details="Actual:"+assertCommand.getActual()+" ; Expected:"+assertCommand.getExpected();
        try {
            extentTest.fail(details, MediaEntityBuilder.createScreenCaptureFromPath(Events.getScreenshot()).build());
        } catch (Exception ioex) {
            System.out.println("problem with file:"+ioex);
        }
    }
}
