package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // NO inicializamos los WebElements en el constructor porque no siempre
    // estarán visibles cuando se construya la clase (algunos sólo aparecen
    // tras hacer login, otros solo antes).
    private static final By SIGN_IN_LINK      = By.linkText("Sign In");
    private static final By WELCOME_DIV       = By.cssSelector("div.text-end.pb-2");
    private static final By USER_MENU_BTN     = By.id("dropdownMenuButton");
    private static final By MY_ACCOUNT_LINK   = By.linkText("My Account");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Servicio: obtener el título de la página de inicio
    public String getTitleHomePage() {
        return driver.getTitle();
    }

    // Servicio: navegar a la página de login haciendo clic en "Sign In"
    // Devuelve la LoginPage, que es la siguiente página del flujo
    public LoginPage goToLogin() {
        WebElement signInLink = wait.until(
                ExpectedConditions.elementToBeClickable(SIGN_IN_LINK));
        signInLink.click();
        return new LoginPage(driver);
    }

    // Servicio: obtener el mensaje de bienvenida tras el login
    // HTML: <div class="text-end pb-2"><i ...></i> Welcome Alyssa!</div>
    public String getWelcomeMessage() {
        WebElement welcomeDiv = wait.until(
                ExpectedConditions.visibilityOfElementLocated(WELCOME_DIV));
        return welcomeDiv.getText().trim();
    }

    // Servicio: navegar a My Account desde el desplegable del icono de usuario
    // Devuelve la MyAccountPage, que es la siguiente página del flujo
    public MyAccountPage goToMyAccount() {
        WebElement userMenuBtn = wait.until(
                ExpectedConditions.elementToBeClickable(USER_MENU_BTN));
        userMenuBtn.click();

        WebElement myAccountLink = wait.until(
                ExpectedConditions.elementToBeClickable(MY_ACCOUNT_LINK));
        myAccountLink.click();

        return new MyAccountPage(driver);
    }
}
