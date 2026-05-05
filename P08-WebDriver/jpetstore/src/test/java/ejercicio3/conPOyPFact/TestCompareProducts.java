package ejercicio3.conPOyPFact;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;

public class TestCompareProducts {

    private WebDriver driver;
    private HomePage poHome;

    private static final String BASE_URL      = "https://jpetstore.aspectran.com";
    private static final String USUARIO       = "j2ee";
    private static final String PASSWORD      = "j2ee";
    private static final String COOKIES_FILE  = "cookies.data";

    // -----------------------------------------------------------------------
    // @BeforeAll: se ejecuta UNA SOLA VEZ antes de todos los tests.
    // Hace login, guarda las cookies en target/cookies.data y cierra el navegador.
    // -----------------------------------------------------------------------
    @BeforeAll
    static void guardarCookies() {
        boolean headless = Boolean.parseBoolean(System.getProperty("chromeHeadless"));
        // storeCookiesToFile abre su propio ChromeDriver internamente
        Cookies.storeCookiesToFile(BASE_URL, USUARIO, PASSWORD, COOKIES_FILE);
    }

    // -----------------------------------------------------------------------
    // @BeforeEach: antes de CADA test cargamos las cookies y navegamos a home
    // ya logueados, sin necesidad de pasar por el formulario de login.
    // -----------------------------------------------------------------------
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

        boolean headless = Boolean.parseBoolean(System.getProperty("chromeHeadless"));
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // loadCookiesFromFile navega al dominio, borra cookies, carga las del fichero
        // y hace refresh() internamente — el navegador queda logueado
        Cookies.loadCookiesFromFile(driver, BASE_URL, COOKIES_FILE);

        poHome = PageFactory.initElements(driver, HomePage.class);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * R5: Comparar productos (gato vs perro) y añadir el gato elegido al carrito.
     *
     *  1. Verificar título de la página y que el usuario está logueado (botón "My Orders").
     *  2. Seleccionar Cats.
     *  3. Verificar que estamos en la página de gatos.
     *  4. Seleccionar el primer producto (gato).
     *  5. Guardar nombre y precio del gato.
     *  6. Guardar la ventana del gato.
     *  7. Navegar a Dogs en una nueva pestaña.
     *  8. Verificar que estamos en la página de perros.
     *  9. Seleccionar el primer producto (perro).
     * 10. Guardar el nombre del perro.
     * 11. Verificar que el nombre del perro es distinto al del gato.
     * 12. Cerrar la pestaña del perro y volver a la del gato.
     * 13. Añadir el gato al carrito.
     * 14. Verificar carrito: página correcta, gato añadido y precio total correcto.
     */
    @Test
    void R5_requirement_compareProducts_And_BuySelectedOne() {

        // PASO 1: Verificamos título y que el usuario está logueado
        Assertions.assertEquals("JPetStore Demo", poHome.getTitleHomePage(),
                "El título de la página de inicio no es el esperado");
        Assertions.assertTrue(poHome.isMyOrdersButtonPresent(),
                "El usuario no está logueado (botón 'My Orders' no encontrado)");

        // PASO 2: Seleccionamos Cats
        CategoryPage poCats = poHome.goToCats();

        // PASO 3: Verificamos que estamos en la página de gatos
        Assertions.assertEquals("Cats", poCats.getCategoryName(),
                "No se ha cargado la página de Cats");

        // PASO 4: Seleccionamos el primer producto (gato)
        ProductPage poGato = poCats.selectFirstProduct();

        // PASO 5: Guardamos nombre y precio del gato
        String nombreGato = poGato.getProductName();
        String precioGato = poGato.getFirstItemPrice();

        // PASO 6: Guardamos el handle de la ventana del gato para poder volver
        String ventanaGato = driver.getWindowHandle();

        // PASO 7: Abrimos Dogs en una nueva pestaña
        // openDogsInNewTab() extrae el href del enlace Dogs, abre nueva pestaña y navega
        CategoryPage poDogs = poHome.openDogsInNewTab();

        // PASO 8: Verificamos que estamos en la página de perros
        Assertions.assertEquals("Dogs", poDogs.getCategoryName(),
                "No se ha cargado la página de Dogs");

        // PASO 9: Seleccionamos el primer producto (perro)
        // Reutilizamos selectFirstProduct() — misma estructura HTML que en Cats
        ProductPage poPerro = poDogs.selectFirstProduct();

        // PASO 10: Guardamos el nombre del perro
        // Reutilizamos getProductName() — misma estructura HTML que en ProductPage de gatos
        String nombrePerro = poPerro.getProductName();

        // PASO 11: Verificamos que el nombre del perro es distinto al del gato
        Assertions.assertNotEquals(nombreGato, nombrePerro,
                "El nombre del gato y del perro no deberían ser iguales");

        // PASO 12: El usuario prefiere el gato — cerramos la pestaña del perro
        // y volvemos a la pestaña del gato
        driver.close();
        driver.switchTo().window(ventanaGato);

        // PASO 13: Añadimos el gato al carrito
        // El driver ya está enfocado en la ProductPage del gato
        poGato = PageFactory.initElements(driver, ProductPage.class);
        CartPage poCart = poGato.addFirstItemToCart();

        // PASO 14: Verificamos el carrito
        Assertions.assertEquals("Shopping Cart", poCart.getCartHeading(),
                "No se ha cargado la página del carrito");
        Assertions.assertTrue(
                poCart.getFirstItemDescription().contains(nombreGato),
                "El producto en el carrito no coincide con el gato seleccionado");
        Assertions.assertEquals(precioGato, poCart.getSubTotal(),
                "El importe total del carrito no coincide con el precio del gato");
    }
}

