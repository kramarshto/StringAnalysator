import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringAnalysatorTest {

    @Test
    void analyse() throws Exception {
        StringAnalysator sa = new StringAnalysator();

        Assertions.assertEquals(-5,sa.analyse("-5"));
        Assertions.assertEquals(7,sa.analyse("5+2"));
        Assertions.assertEquals(4.5,sa.analyse("9/2"));
        Assertions.assertEquals(2,sa.analyse("9%7"));
        Assertions.assertEquals(16,sa.analyse("4^2"));
        Assertions.assertEquals(10,sa.analyse("(5*4)/2"));
        Assertions.assertEquals(100, sa.analyse("(2 *5)^(5% 3)"));
    }
}