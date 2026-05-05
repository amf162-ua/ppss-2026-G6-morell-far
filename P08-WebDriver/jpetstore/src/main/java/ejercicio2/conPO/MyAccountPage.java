package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {

    private final WebDriver driver;

    // Ambos elementos pueden localizarse en el constructor porque
    // están visibles en cuanto se carga la página de cuenta.
    private final WebElement userInfoHeading;
    private final WebElement firstNameInput;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // HTML: <h3>User Information</h3>
        userInfoHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[text()='User Information']")));

        // HTML: <input type="text" name="firstName" value="Alyssa" class="form-control">
        firstNameInput  = driver.findElement(By.name("firstName"));
    }

    // Servicio: obtener el primer encabezamiento del formulario
    public String getFormHeading() {
        return userInfoHeading.getText();
    }

    // Servicio: obtener el FirstName del usuario
    // Los <input> no devuelven valor con getText(); se usa getDomProperty("value")
    public String getFirstName() {
        return firstNameInput.getDomProperty("value");
    }
}
