package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PremioTest {

    IMocksControl ctrl;
    ClienteWebService clienteMock;
    Premio premio;

    @BeforeEach
    void setUp() {
        // Paso 1: crear el StrictControl (coordina el orden entre todos los mocks)
        ctrl = EasyMock.createStrictControl();

        // clienteMock asociado al mismo ctrl
        clienteMock = ctrl.mock(ClienteWebService.class);

        // partial mock de Premio también asociado al mismo ctrl
        premio = EasyMock.partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock(ctrl);  // hereda el strict del ctrl

        // Inyectamos el clienteMock en el partial mock
        premio.cliente = clienteMock;
    }

    @Test
    void C1_compruebaPremio_should_return_premiado_when_number_is_less_than_0_1()
            throws ClienteWebServiceException {
        // Arrange
        EasyMock.expect(premio.generaNumero()).andReturn(0.07f);
        EasyMock.expect(clienteMock.obtenerPremio()).andReturn("entrada final Champions");
        ctrl.replay(); // activa TODOS los mocks del control

        // Act
        String resultado = premio.compruebaPremio();

        // Assert
        assertEquals("Premiado con entrada final Champions", resultado);
        ctrl.verify(); // verifica TODOS los mocks del control
    }

    @Test
    void C2_compruebaPremio_should_return_error_message_when_service_fails() throws ClienteWebServiceException{
        EasyMock.expect(premio.generaNumero()).andReturn(0.05f);

        // Aquí simulamos que el servicio lanza una excepción en lugar de devolver un String
        EasyMock.expect(clienteMock.obtenerPremio())
                .andThrow(new ClienteWebServiceException("Error de conexión"));

        ctrl.replay();

        // 2. Act
        String resultado = premio.compruebaPremio();

        // 3. Assert
        assertEquals("No se ha podido obtener el premio", resultado);

        // 4. Verify
        ctrl.verify();
    }

    @Test
    void C3_compruebaPremio_should_return_sin_premio_when_number_is_0_48() {
        // 1. Arrange
        EasyMock.expect(premio.generaNumero()).andReturn(0.48f);

        // IMPORTANTE: Al ser > 0.1, no esperamos llamadas a clienteMock.
        // Si tu código real lo llamara por error, ctrl.verify() lanzaría un fallo.

        ctrl.replay();

        // 2. Act
        String resultado = premio.compruebaPremio();

        // 3. Assert
        assertEquals("Sin premio", resultado);

        // 4. Verify
        ctrl.verify();
    }
}