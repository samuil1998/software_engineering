import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class TestExample {

    @Test
    public void TestSomething()
    {
        assertThat(1+1, is(2));
    }

}
