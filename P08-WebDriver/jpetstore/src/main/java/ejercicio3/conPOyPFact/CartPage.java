package ejercicio3.conPOyPFact;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Título de la página del carrito
    // HTML: <h3>Shopping Cart</h3>
    @FindBy(css = "div.d-flex h3")
    private WebElement cartHeading;

    // Nombre del producto en la descripción del carrito (primera fila, columna Description)
    // HTML: <td align="left"> Tailless  Manx </td>  → columna 3 (índice 3)
    // Usamos la columna 2 (Product ID) que tiene texto limpio: "FL-DSH-01"
    // En realidad queremos comparar por nombre de producto (el h3 de la ProductPage era "Manx")
    // La descripción incluye el nombre: "Tailless Manx" → contiene el nombre del producto
    @FindBy(css = "tbody.table-group-divider tr:first-child td:nth-child(3)")
    private WebElement firstItemDescription;

    // Precio total del carrito (Sub Total en el tfoot)
    // HTML: <tfoot> ... <td><strong>$58.50</strong></td>
    @FindBy(css = "tfoot strong:last-of-type")
    private WebElement subTotal;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Servicio: obtener el título de la página del carrito
    public String getCartHeading() {
        wait.until(ExpectedConditions.visibilityOf(cartHeading));
        return cartHeading.getText();
    }

    // Servicio: obtener la descripción del primer ítem en el carrito
    // La descripción contiene el nombre del producto (ej: "Tailless Manx")
    public String getFirstItemDescription() {
        wait.until(ExpectedConditions.visibilityOf(firstItemDescription));
        return firstItemDescription.getText().trim();
    }

    // Servicio: obtener el importe total (Sub Total) del carrito
    public String getSubTotal() {
        return driver.findElement(
                By.xpath("//td[normalize-space()='Sub Total:']/following-sibling::td/strong")
        ).getText().trim();
    }
}
