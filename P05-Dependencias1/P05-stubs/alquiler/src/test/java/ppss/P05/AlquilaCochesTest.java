package ppss.P05;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AlquilaCochesTest {

    @Test
    void C1_calculaPrecio_should_return_ticket_with_price_75_when_no_days_are_holidays() throws MensajeException {

        // Arrange
        CalendarioStub calendarioStub = new CalendarioStub();
        IService servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable(calendarioStub, servicioStub);

        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fechaInicio = LocalDate.of(2024,5,18);
        int dias = 10;

        // Para que el test funcione, sobreescribimos temporalmente el servicio con ServicioStub



        // Act
        Ticket resultado = sut.calculaPrecio(tipo, fechaInicio, dias);

        // Assert
        assertEquals(75, resultado.getPrecio_final());
    }

    @Test
    void C1_calculaPrecio_should_return_ticket_with_price_62_5_when_just_2_days_are_holidays() throws MensajeException {

        // Arrange
        CalendarioStub calendarioStub = new CalendarioStub();
        IService servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable(calendarioStub, servicioStub);

        TipoCoche tipo = TipoCoche.CARAVANA;
        LocalDate fechaInicio = LocalDate.of(2024,6,19);
        int dias = 7;

        // Para que el test funcione, sobreescribimos temporalmente el servicio con ServicioStub



        // Act
        Ticket resultado = sut.calculaPrecio(tipo, fechaInicio, dias);

        // Assert
        assertEquals(62,5, resultado.getPrecio_final());
    }

    @Test
    void C3_calculaPrecio_should_throw_mensajeException_when_some_days_fail() {

        // Arrange
        CalendarioStub calendarioStub = new CalendarioStub();
        ServicioStub servicioStub = new ServicioStub(); // precio fijo 10€
        AlquilaCochesTestable sut = new AlquilaCochesTestable(calendarioStub, servicioStub);

        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fechaInicio = LocalDate.of(2024,4,17);
        int dias = 8;

        // Act & Assert
        MensajeException ex = assertThrows(MensajeException.class, () -> {
            sut.calculaPrecio(tipo, fechaInicio, dias);
        });

        // Comprobamos que el mensaje de error tiene exactamente los días fallidos
        String esperado = "Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ";
        assertEquals(esperado, ex.getMessage());
    }
}