package ppss.practica3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Tests asociados a la clase Cine")
class CineTest {
    private Cine cine;

    @BeforeEach
    public void setUp(){
        cine = new Cine();
    }

    @Test
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
}