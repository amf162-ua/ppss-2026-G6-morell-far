package ejercicio2.conPO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;

public class TestLogin2 {

    private WebDriver driver;
    private HomePage poHome;

    private static final String BASE_URL          = "https://jpetstore.aspectran.com";
    private static final String USUARIO_VALIDO    = "j2ee";
    private static final String PASSWORD_VALIDO   = "j2ee";
    private static final String PASSWORD_INVALIDO = "abcd";

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(BASE_URL);

        // La primera Page Object se crea aquí y se le pasa el WebDriver
        poHome = new HomePage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * R3: Login correcto con Page Object.
     * Equivalente a R1, pero el test trabaja con métodos de las PO
     * en lugar de con locators y WebElements directamente.
     */
    @Test
    public void R3_requierement_PO_loginOK_should_login_with_success_when_user_account_exists() {

        // Verificamos el título de la página de inicio
        Assertions.assertEquals("JPetStore Demo", poHome.getTitleHomePage(),
                "El título de la página de inicio no es el esperado");

        // Navegamos al login — goToLogin() hace clic en "Sign In" y devuelve LoginPage
        LoginPage poLogin = poHome.goToLogin();

        // Verificamos el encabezado del formulario de login
        Assertions.assertTrue(
                poLogin.getFormHeading().contains("Please enter your username and password"),
                "No se ha cargado correctamente el formulario de login");

        // Hacemos login — loginSuccess() rellena el formulario, lo envía y devuelve HomePage
        poHome = poLogin.loginSuccess(USUARIO_VALIDO, PASSWORD_VALIDO);

        // Guardamos el mensaje de bienvenida desde la home
        String mensajeBienvenida = poHome.getWelcomeMessage();

        // Navegamos a My Account — goToMyAccount() abre el desplegable y devuelve MyAccountPage
        MyAccountPage poAccount = poHome.goToMyAccount();

        // Verificamos el encabezamiento "User Information"
        Assertions.assertTrue(
                poAccount.getFormHeading().contains("User Information"),
                "No se ha cargado correctamente la página de cuenta de usuario");

        // Leemos el FirstName
        String nombreReal = poAccount.getFirstName();

        // Verificamos que el mensaje de bienvenida coincide con "Welcome <FirstName>!"
        Assertions.assertEquals(
                "Welcome " + nombreReal + "!",
                mensajeBienvenida,
                "El mensaje de bienvenida no coincide con el nombre real del usuario");
    }

    /**
     * R4: Login fallido con Page Object.
     * Equivalente a R2, pero usando métodos de las PO.
     */
    @Test
    public void R4_requierement_PO_loginFailed_should_fail_when_user_account_not_exists() {

        // Verificamos el título de la página de inicio
        Assertions.assertEquals("JPetStore Demo", poHome.getTitleHomePage(),
                "El título de la página de inicio no es el esperado");

        // Navegamos al login
        LoginPage poLogin = poHome.goToLogin();

        // Verificamos el encabezado del formulario de login
        Assertions.assertTrue(
                poLogin.getFormHeading().contains("Please enter your username and password"),
                "No se ha cargado correctamente el formulario de login");

        // Intentamos login con password incorrecto — loginFailure() envía el formulario
        // y permanecemos en la misma LoginPage (el servidor devuelve el formulario con error)
        poLogin.loginFailure(USUARIO_VALIDO, PASSWORD_INVALIDO);

        // Verificamos el mensaje de error
        String errorText = poLogin.getErrorMessage();
        Assertions.assertTrue(
                errorText.contains("Invalid username or password.") &&
                        errorText.contains("Signon failed."),
                "No aparece el mensaje de error esperado tras un login fallido");
    }
}
