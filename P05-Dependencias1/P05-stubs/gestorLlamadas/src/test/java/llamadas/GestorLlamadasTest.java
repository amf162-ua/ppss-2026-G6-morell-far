package llamadas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {

    @Test
    void C1_calculaConsumo_should_return_147_when_minutos_10_and_hora_15() {
        //Arrange

        CalendarioStub stub = new CalendarioStub(15);
        GestorLlamadasTestable gestor = new GestorLlamadasTestable(stub);
        //Act
        double resultado = gestor.calculaConsumo(10);
        //Assert
        assertEquals(147, resultado);
    }

    @Test
    void C2_calculaConsumo_should_return_65_when_minutos_10_and_hora_23() {

        CalendarioStub stub = new CalendarioStub(23);
        GestorLlamadasTestable gestor = new GestorLlamadasTestable(stub);

        double resultado = gestor.calculaConsumo(10);

        assertEquals(65.0, resultado);
    }
}