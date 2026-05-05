package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // PASO 1: Usamos List<WebElement> para "My Orders" — si no está presente la lista estará vacía
    // (no lanza excepción como haría un WebElement único)
    @FindBy(linkText = "My Orders")
    private List<WebElement> myOrdersButtonList;

    // Enlace a la categoría Cats en la barra de navegación
    @FindBy(linkText = "Cats")
    private WebElement linkCats;

    // Enlace a la categoría Dogs — lo necesitamos para extraer su href (paso 7)
    @FindBy(linkText = "Dogs")
    private WebElement linkDogs;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Servicio: obtener el título de la página de inicio
    public String getTitleHomePage() {
        return driver.getTitle();
    }

    // Servicio: comprobar si el botón "My Orders" está presente (usuario logueado)
    // Si encuentra el botón, la lista tendrá tamaño 1; si no, estará vacía
    public boolean isMyOrdersButtonPresent() {
        return !myOrdersButtonList.isEmpty();
    }

    // Servicio: navegar a la categoría Cats y devolver la CategoryPage correspondiente
    public CategoryPage goToCats() {
        wait.until(ExpectedConditions.elementToBeClickable(linkCats)).click();
        return PageFactory.initElements(driver, CategoryPage.class);
    }

    // Servicio: abrir Dogs en una nueva pestaña (paso 7)
    // Extrae la URL del enlace Dogs y la abre en una nueva pestaña
    public CategoryPage openDogsInNewTab() {
        String url = linkDogs.getDomProperty("href");
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get(url);
        return PageFactory.initElements(driver, CategoryPage.class);
    }
}
