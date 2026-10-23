import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private PasswordChecker checker;

    @BeforeEach
    void setUp() {
        checker = new PasswordChecker();
    }

    //Test de prueba
    @Test
    void passwordCorrecto() {
        int resultado = checker.check("abcde123", "abcde123");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    // TESTS DE LONGITUD:

    //comprobar que una longitud menor de la indicada en minlength da error
    @ParameterizedTest
    @ValueSource(ints = { 1,5,7})
    void longitudMinimaIncorrecta(int longitud) {
        checker.setMinLength(8);

        String password = "A".repeat(longitud);

        assertEquals(
                PasswordChecker.ERR_LENGTH,
                checker.check(password, password)
        );
    }

    //comprobar que una longitud mayor de la indicada en maxlength da error
    //El test está bien diseñado pero da error. Habría que comunicarselo a desarrollo
    @ParameterizedTest
    @ValueSource(ints = { 11,23})
    void longitudMaximaIncorrecta(int longitud) {
        checker.setMaxLength(10);

        String password = "A".repeat(longitud);

        assertEquals(
                PasswordChecker.ERR_LENGTH,
                checker.check(password, password)
        );
    }

    // Comprobar que la longitud mínima indicada en minLength es válida
    @ParameterizedTest
    @ValueSource(ints = {8, 9, 10})
    void longitudMinimaCorrecta(int longitud) {
        checker.setMinLength(8);

        String password = "A".repeat(longitud);

        assertEquals(
                PasswordChecker.PASSWORD_OK,
                checker.check(password, password)
        );
    }

    // Comprobar que la longitud máxima indicada en maxLength es válida
    @ParameterizedTest
    @ValueSource(ints = {8, 9, 10})
    void longitudMaximaCorrecta(int longitud) {
        checker.setMaxLength(10);

        String password = "A".repeat(longitud);

        assertEquals(
                PasswordChecker.PASSWORD_OK,
                checker.check(password, password)
        );
    }

    //TEST DE DIGITOS:

    // Comprobar que una contraseña sin dígitos da error
    @ParameterizedTest
    @ValueSource(strings = {"Abcdefgh", "Password", "HolaMundo"})
    void faltaDigito(String password) {
        checker.setCheckDigits(true);

        assertEquals(
                PasswordChecker.ERR_DIGITS,
                checker.check(password, password)
        );
    }

    // Comprobar que se acepta una contraseña con y sin dígitos cuando el check de dígitos está desactivado
    @ParameterizedTest
    @ValueSource(strings = {"Abcdefgh", "Password", "HolaMundo1"})
    void checkDigitosDesactivado(String password) {
        checker.setCheckDigits(false);

        assertEquals(
                PasswordChecker.PASSWORD_OK,
                checker.check(password, password)
        );
    }

    //TEST DE MAYUSCULAS/MINUSCULAS

    // Comprobar que una contraseña sin mayúsculas da error
    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh", "password", "holamundo"})
    void faltaMayusculas(String password) {
        checker.setCheckUppers(true);

        assertEquals(
                PasswordChecker.ERR_UPPERS,
                checker.check(password, password)
        );
    }

    // Comprobar que una contraseña sin minúsculas da error
    @ParameterizedTest
    @ValueSource(strings = {"ABCDEFGH", "PASSWORD", "HOLAMUNDO"})
    void faltaMinusculas(String password) {
        checker.setCheckLowers(true);

        assertEquals(
                PasswordChecker.ERR_LOWERS,
                checker.check(password, password)
        );
    }

    // Comprobar que se acepta una contraseña sin mayúsculas cuando el check está desactivado
    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh", "password", "Holamundo"})
    void checkMayusculasDesactivado(String password) {
        checker.setCheckUppers(false);

        assertEquals(
                PasswordChecker.PASSWORD_OK,
                checker.check(password, password)
        );
    }

    // Comprobar que una contraseña sin minúsculas se acepta cuando el check está desactivado
    @ParameterizedTest
    @ValueSource(strings = {"ABCDEFGH", "PAssword", "holamundo"})
    void checkMinusculasDesactivado(String password) {
        checker.setCheckLowers(false);

        assertEquals(
                PasswordChecker.PASSWORD_OK,
                checker.check(password, password)
        );
    }

    //TEST DE COINCIDENCIA

    // Comprobar que dos contraseñas iguales se aceptan
    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh", "password", "HOLAMUNDO"})
    void contrasenasIguales(String password) {
        assertEquals(
                PasswordChecker.PASSWORD_OK,
                checker.check(password, password)
        );
    }

    // Comprobar que dos contraseñas diferentes dan error
    @ParameterizedTest
    @ValueSource(strings = {"password1", "HolaMundo", "ABCDEFGH"})
    void contrasenasDiferentes(String password) {
        assertEquals(
                PasswordChecker.ERR_NO_MATCH,
                checker.check(password, password + "hola")
        );
    }

    // TEST DE CONTRASEÑA EN BLANCO

    //Comprobar que contraseña vacia da error
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void contrasenaEnBlanco(String password) {
        assertEquals(
                PasswordChecker.ERR_BLANK,
                checker.check(password, password)
        );
    }

    // TESTS DE CONFIGURACIÓN

    // Comprobar que la longitud mínima no puede ser negativa
    @Test
    void longitudMinimaNegativa() {
        assertThrows(
                IllegalArgumentException.class,
                () -> checker.setMinLength(-1)
        );
    }

    // Comprobar que la longitud máxima no puede ser inferior a la mínima
    @Test
    void longitudMaximaInferiorAMinima() {
        checker.setMinLength(10);

        assertThrows(
                IllegalArgumentException.class,
                () -> checker.setMaxLength(9)
        );
    }

    // TESTS DE GETTERS

    // Comprobar el valor inicial de la longitud mínima
    @Test
    void getMinLengthInicial() {
        assertEquals(8, checker.getMinLength());
    }

    // Comprobar el valor inicial de la longitud máxima
    @Test
    void getMaxLengthInicial() {
        assertEquals(Integer.MAX_VALUE, checker.getMaxLength());
    }

    // Comprobar los valores iniciales de las comprobaciones
    @Test
    void checksIniciales() {
        assertFalse(checker.getCheckDigits());
        assertFalse(checker.getCheckUppers());
        assertFalse(checker.getCheckLowers());
    }

}


