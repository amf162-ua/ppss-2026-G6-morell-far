package ejercicio3.conPOyPFact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Nombre del producto: encabezado de la página
    // HTML: <h3>Manx</h3>
    @FindBy(css = "div.d-flex h3")
    private WebElement productName;

    // Precio del primer ítem en la tabla
    // HTML: <tbody class="table-group-divider"> <tr> <td>$58.50</td>
    // Es la 4ª columna (índice 3) de la primera fila
    @FindBy(css = "tbody.table-group-divider tr:first-child td:nth-child(4)")
    private WebElement firstItemPrice;

    // Botón "Add to Cart" del primer ítem
    // HTML: <a class="btn btn-primary btn-sm" href="/cart/addItemToCart?itemId=EST-14">Add to Cart</a>
    @FindBy(css = "tbody.table-group-divider tr:first-child a.btn.btn-primary")
    private WebElement firstItemAddToCartButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Servicio: obtener el nombre del producto (reutilizable para gato y perro)
    public String getProductName() {
        wait.until(ExpectedConditions.visibilityOf(productName));
        return productName.getText();
    }

    // Servicio: obtener el precio del primer ítem
    public String getFirstItemPrice() {
        wait.until(ExpectedConditions.visibilityOf(firstItemPrice));
        return firstItemPrice.getText();
    }

    // Servicio: añadir el primer ítem al carrito y devolver la CartPage
    public CartPage addFirstItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(firstItemAddToCartButton)).click();
        return PageFactory.initElements(driver, CartPage.class);
    }
}

