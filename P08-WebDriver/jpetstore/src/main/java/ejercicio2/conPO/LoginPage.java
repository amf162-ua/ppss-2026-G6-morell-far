package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Localizamos en el constructor los elementos que ya están visibles
    // al llegar a esta página:
    private final WebElement usernameField;
    private final WebElement passwordField;
    private final WebElement loginButton;
    private final WebElement formHeading;

    // El mensaje de error NO se puede localizar en el constructor:
    // solo aparece en el DOM tras enviar el formulario con datos incorrectos.
    private static final By ERROR_MSG = By.cssSelector("div.alert.alert-danger");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperamos a que el formulario sea visible antes de localizar sus elementos
        // HTML: <h5 class="card-title">Please enter your username and password.</h5>
        formHeading   = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h5.card-title")));

        // HTML: <input type="text"     id="username" name="username" ...>
        // HTML: <input type="password" id="password" name="password" ...>
        // HTML: <button type="submit"  class="btn btn-primary">Login</button>
        usernameField = driver.findElement(By.id("username"));
        passwordField = driver.findElement(By.id("password"));
        loginButton   = driver.findElement(By.cssSelector("button.btn.btn-primary"));
    }

    // Servicio: obtener el título del formulario de login
    public String getFormHeading() {
        return formHeading.getText();
    }

    // Servicio: login correcto — rellena credenciales, envía y devuelve HomePage
    // (tras login exitoso volvemos a la home, donde aparece el mensaje de bienvenida)
    public HomePage loginSuccess(String username, String password) {
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();
        return new HomePage(driver);
    }

    // Servicio: login fallido — rellena credenciales incorrectas y envía el formulario
    // No devuelve nueva página porque el servidor vuelve a mostrar el mismo formulario
    public void loginFailure(String username, String password) {
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();
    }

    // Servicio: obtener el mensaje de error tras un login fallido
    // El elemento solo existe en el DOM después de enviar el formulario con datos incorrectos
    public String getErrorMessage() {
        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(ERROR_MSG));
        return errorMsg.getText().trim();
    }
}
