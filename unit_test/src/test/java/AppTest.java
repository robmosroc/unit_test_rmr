import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    private PasswordChecker checker;

    @BeforeEach
    void setUp() {
        checker = new PasswordChecker();
    }

    @Test
    void passwordCorrecto() {
        int resultado = checker.check("abcde123", "abcde123");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    
}


