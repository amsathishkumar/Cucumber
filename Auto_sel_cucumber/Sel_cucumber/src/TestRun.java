
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(format = {"pretty","html:target/cucumber-html-report","json-pretty:targert/cucumber-report.json"})
public class TestRun {

}
