package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotifyCenterTest {
    private IMocksControl ctrl;
    private MailServer mailServerMock;
    private NotifyCenter sut; // Partial Mock

    @BeforeEach
    void setUp() {
        ctrl = EasyMock.createStrictControl();
        mailServerMock = ctrl.mock(MailServer.class);

        // Creamos el Partial Mock de la SUT
        sut = EasyMock.partialMockBuilder(NotifyCenter.class)
                .addMockedMethod("getServer")
                .addMockedMethod("getToday")
                .addMockedMethod("sendNotify")
                .mock(ctrl);
    }

    @Test
    void testA_fallosParciales() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 3, 23);
        List<String> emails = Arrays.asList("email1", "email2", "email3", "email4");

        // EXPECTATIVAS (Orden estricto)
        EasyMock.expect(sut.getServer()).andReturn(mailServerMock);
        EasyMock.expect(sut.getToday()).andReturn(fecha);
        EasyMock.expect(mailServerMock.findMailItemsWithDate(fecha)).andReturn(emails);

        // email1: OK
        sut.sendNotify("email1");
        // email2: FALLO
        sut.sendNotify("email2");
        EasyMock.expectLastCall().andThrow(new FailedNotifyException("Error email2"));
        // email3: FALLO
        sut.sendNotify("email3");
        EasyMock.expectLastCall().andThrow(new FailedNotifyException("Error email3"));
        // email4: OK
        sut.sendNotify("email4");

        ctrl.replay();

        FailedNotifyException ex = assertThrows(FailedNotifyException.class, () ->
                sut.notifyUsers(fecha)
        );
        assertEquals("Failures during sending process", ex.getMessage());

        ctrl.verify();
    }

    @Test
    void testB_errorFecha() throws Exception {
        LocalDate envio = LocalDate.of(2026, 3, 24);
        LocalDate hoy = LocalDate.of(2026, 2, 2);

        EasyMock.expect(sut.getServer()).andReturn(mailServerMock);
        EasyMock.expect(sut.getToday()).andReturn(hoy);

        ctrl.replay();

        FailedNotifyException ex = assertThrows(FailedNotifyException.class, () ->
                sut.notifyUsers(envio)
        );
        assertEquals("Date error", ex.getMessage());

        ctrl.verify();
    }

    @Test
    void testC_listaVacia() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 3, 7);

        EasyMock.expect(sut.getServer()).andReturn(mailServerMock);
        EasyMock.expect(sut.getToday()).andReturn(fecha);
        EasyMock.expect(mailServerMock.findMailItemsWithDate(fecha)).andReturn(Collections.emptyList());

        ctrl.replay();

        // No debe lanzar excepción
        assertDoesNotThrow(() -> sut.notifyUsers(fecha));

        ctrl.verify();
    }
}