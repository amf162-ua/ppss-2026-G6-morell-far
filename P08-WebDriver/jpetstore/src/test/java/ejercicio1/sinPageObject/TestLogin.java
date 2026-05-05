package ejercicio1.sinPageObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class TestLogin {

    private WebDriver driver;
    private WebDriverWait wait;

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

        // Wait explícito compartido para todos los elementos del test
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * R1: Login correcto con usuario existente.
     *
     * 1. Verificar título de la página de inicio ("JPetStore Demo").
     * 2. Clic en "Sign In".
     * 3. Verificar encabezado del formulario de login.
     * 4. Rellenar usuario j2ee / password j2ee y enviar.
     * 5. Guardar el mensaje de bienvenida.
     * 6. Navegar a MY ACCOUNT desde el desplegable del icono de usuario.
     * 7. Verificar encabezamiento "User Information".
     * 8. Leer el FirstName del formulario.
     * 9. Verificar que "Welcome <FirstName>!" coincide con el mensaje del paso 5.
     */
    @Test
    public void R1_requirement_loginOK_should_login_with_success_when_user_account_exists() {

        // PASO 1: Verificamos el título de la página de inicio
        Assertions.assertEquals("JPetStore Demo", driver.getTitle());

        // PASO 2: Clic en "Sign In"
        WebElement signInLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();

        // PASO 3: Verificamos el encabezado del formulario de login
        // HTML real: <h5 class="card-title">Please enter your username and password.</h5>
        WebElement loginHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("h5.card-title")));
        Assertions.assertTrue(
                loginHeading.getText().contains("Please enter your username and password"));

        // PASO 4: Rellenamos usuario y password y enviamos el formulario
        // HTML: <input type="text" id="username" name="username" ...>
        // HTML: <input type="password" id="password" name="password" ...>
        WebElement userField = driver.findElement(By.id("username"));
        userField.clear();
        userField.sendKeys(USUARIO_VALIDO);

        WebElement passField = driver.findElement(By.id("password"));
        passField.clear();
        passField.sendKeys(PASSWORD_VALIDO);

        // HTML: <button type="submit" class="btn btn-primary">Login</button>
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        // PASO 5: Guardamos el mensaje de bienvenida
        // HTML: <div class="text-end pb-2"><i class="bi bi-person-circle"></i> Welcome Alyssa!</div>
        WebElement welcomeDiv = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.text-end.pb-2")));
        String mensajeBienvenida = welcomeDiv.getText().trim();

        // PASO 6: Abrimos el desplegable del icono de usuario y hacemos clic en "My Account"
        // HTML: <a class="btn btn-secondary dropdown-toggle" id="dropdownMenuButton" ...>
        WebElement userMenuBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("dropdownMenuButton")));
        userMenuBtn.click();

        // HTML: <a class="dropdown-item" href="/account/editAccountForm">My Account</a>
        WebElement myAccountLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("My Account")));
        myAccountLink.click();

        // PASO 7: Verificamos el encabezamiento "User Information"
        // HTML: <h3>User Information</h3>
        WebElement userInfoHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h3[text()='User Information']")));
        Assertions.assertTrue(
                userInfoHeading.getText().contains("User Information"));

        // PASO 8: Leemos el FirstName del formulario
        // HTML: <input type="text" name="firstName" value="Alyssa" class="form-control">
        // Los <input> no devuelven texto con getText(); hay que usar getDomProperty("value")
        WebElement firstNameInput = driver.findElement(By.name("firstName"));
        String nombreReal = firstNameInput.getDomProperty("value");

        // PASO 9: Verificamos que el mensaje de bienvenida es "Welcome <FirstName>!"
        Assertions.assertEquals(
                "Welcome " + nombreReal + "!", mensajeBienvenida);
    }

    /**
     * R2: Login fallido con contraseña incorrecta.
     *
     * 1. Verificar título de la página de inicio ("JPetStore Demo").
     * 2. Clic en "Sign In".
     * 3. Verificar encabezado del formulario de login.
     * 4. Rellenar el campo password con valor incorrecto ("abcd").
     * 5. Enviar el formulario.
     * 6. Verificar que aparece "Invalid username or password. Signon failed."
     */
    @Test
    public void R2_requirement_loginFailed_should_fail_when_user_account_not_exists() {

        // PASO 1: Verificamos el título de la página de inicio
        Assertions.assertEquals("JPetStore Demo", driver.getTitle());

        // PASO 2: Clic en "Sign In"
        WebElement signInLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();

        // PASO 3: Verificamos el encabezado del formulario de login
        // HTML real: <h5 class="card-title">Please enter your username and password.</h5>
        WebElement loginHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("h5.card-title")));
        Assertions.assertTrue(
                loginHeading.getText().contains("Please enter your username and password"));

        // PASO 4: Rellenamos usuario correcto y password incorrecto
        WebElement userField = driver.findElement(By.id("username"));
        userField.clear();
        userField.sendKeys(USUARIO_VALIDO);

        WebElement passField = driver.findElement(By.id("password"));
        passField.clear();                        // limpiamos el campo antes de escribir
        passField.sendKeys(PASSWORD_INVALIDO);    // password incorrecto

        // PASO 5: Enviamos el formulario
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        // PASO 6: Verificamos el mensaje de error
        // HTML: <div class="alert alert-danger mt-3">Invalid username or password.  Signon failed.</div>
        // Usamos cssSelector en lugar de XPath con texto exacto para evitar problemas con espacios
        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.alert.alert-danger")));
        Assertions.assertTrue(
                errorMsg.getText().contains("Invalid username or password.") &&
                        errorMsg.getText().contains("Signon failed."));
    }
}
