package ppss.practica3;

import com.sun.jdi.connect.Connector;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Tests asociados a la clase Cine")
class CineTest {
    private Cine cine;

    @BeforeEach
    public void setUp(){
        cine = new Cine();
    }

    @Test
    @Tag("no_parametrizado")
    public void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3(){
        //Arrange
        boolean[] asientos = new boolean[0];
        int solicitados = 3;

        //Act
        ButacasException exception = assertThrows(
                ButacasException.class,
                () -> cine.reservaButacas(asientos, solicitados)
                );

        assertEquals("No se puede procesar la solicitud", exception.getMessage());
    }

    @Test
    @Tag("no_parametrizado")
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero() {
        //Arrange
        boolean[] asientos = new boolean[0];
        int solicitados = 0;
        boolean esperado = false;

        //Act
        boolean resultado = cine.reservaButacas(asientos, solicitados);

        //Assert
        assertEquals(esperado, resultado);
    }

    @Test
    @Tag("no_parametrizado")
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2(){
        //Arrange
        boolean[] asientos = {false, false, false, true, true};
        int solicitados = 2;
        boolean esperado = true;
        boolean[] esperadoArray = {true, true, false, true, true};

        //Act
        boolean resultado = cine.reservaButacas(asientos, solicitados);

        //Assert
        assertAll("Comprobaciones de reserva",
                () -> assertEquals(esperado, resultado),
                () -> assertArrayEquals(esperadoArray, asientos)
                );
    }

    @Test
    @Tag("no_parametrizado")
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1(){
        //Arrange
        boolean[] asientos = {true, true, true};
        int solicitados = 1;
        boolean esperado = false;
        boolean[] esperadoArray = {true, true, true};

        //Act
        boolean resultado = cine.reservaButacas(asientos, solicitados);

        //Assert
        assertAll("Comprobaciones de reserva",
                () -> assertEquals(esperado, resultado),
                () -> assertArrayEquals(esperadoArray, asientos)
        );
    }


    @DisplayName("reservaButacas_")
    @ParameterizedTest(name = "reservaButacas_[{index}] should be {2} when we want {0} and {3}")
    @MethodSource("datosC5")
    @Tag("parametrizado")
    void C5_reservaButacas(int numButacas, boolean[] fila, boolean esperado, String descripcion){
        boolean resultado = cine.reservaButacas(fila, numButacas);
        assertEquals(esperado, resultado, () -> "reservaButacas failed: " + descripcion);
    }

    private static Stream<Arguments> datosC5() {
        return Stream.of(
                Arguments.of(
                  0,
                  new boolean[0],
                  false,
                  "fila has no seats"
                ),
                Arguments.of(
                      2,
                      new boolean[]{false, false, false, true, true},
                      true,
                      "there ara 2 free seats"
                ),
                Arguments.of(
                        1,
                        new boolean[]{true, true, true},
                        false,
                        "all seats are already reserves"
                )
        );
    }

    @DisplayName("reservaButacas_")
    @ParameterizedTest(name = "reservaButacas_[{index}] should be {2} when we want {0} and {3}")
    @MethodSource("datosC6")
    @Tag("parametrizado")
    @Tag("tablaB")
    void C6_reservaButacas(int numButacas, boolean[] fila, boolean esperado, String descripcion){
        boolean resultado = cine.reservaButacas(fila, numButacas);
        assertEquals(esperado, resultado, () -> "reservaButacas failed: " + descripcion);
    }

    private static Stream<Arguments> datosC6() {
        return Stream.of(
                Arguments.of(
                        1,
                        new boolean[]{false, false, false},
                        true,
                        "fila has 3 free seats"
                ),
                Arguments.of(
                        1,
                        new boolean[]{true},
                        false,
                        "there ara no free seats"
                )
        );
    }
}