package ejercicio3.conPOyPFact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Encabezado de la página de categoría
    // HTML: <h3>Cats</h3>  /  <h3>Dogs</h3>
    @FindBy(css = "div.d-flex h3")
    private WebElement categoryHeading;

    // Primer enlace de producto en la tabla (primera fila, primera celda)
    // HTML: <tbody class="table-group-divider"> <tr> <td> <a href="/products/FL-DSH-01">FL-DSH-01</a>
    @FindBy(css = "tbody.table-group-divider tr:first-child td:first-child a")
    private WebElement firstProductLink;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Servicio: obtener el nombre de la categoría (encabezado de la página)
    public String getCategoryName() {
        wait.until(ExpectedConditions.visibilityOf(categoryHeading));
        return categoryHeading.getText();
    }

    // Servicio: seleccionar el primer producto de la lista y devolver su ProductPage
    // Reutilizable tanto para gatos (paso 4) como para perros (paso 9),
    // ya que ambas páginas tienen la misma estructura HTML
    public ProductPage selectFirstProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(firstProductLink)).click();
        return PageFactory.initElements(driver, ProductPage.class);
    }
}

