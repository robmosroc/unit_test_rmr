import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

public class mainTest {
    PasswordChecker checker = new PasswordChecker();

    @BeforeEach
    void setUp() {
        checker = new PasswordChecker();
    }

    @Test
    public void passwordCorrecto() {

        int resultado = checker.check("abcde123", "abcde123");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    public void setMaxLength(){

        checker.setMaxLength(8);
    }

}
