package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    private IMocksControl ctrl;
    private FileReader readerMock; // El objeto que será inyectado
    private FicheroTexto sut;      // El objeto que estamos probando (Partial Mock)

    @BeforeEach
    void setUp() {
        ctrl = EasyMock.createStrictControl();
        readerMock = ctrl.mock(FileReader.class);
        sut = EasyMock.partialMockBuilder(FicheroTexto.class)
                .addMockedMethod("crearFileReader")
                .mock(ctrl); // Importante: usar el mismo ctrl
    }

    @Test
    void C1_should_return_Exception_when_reading_file() throws Exception{
        String nombre = "src/test/resources/ficheroC1.txt";
        EasyMock.expect(sut.crearFileReader(nombre)).andReturn(readerMock);

        EasyMock.expect(readerMock.read()).andReturn((int)'a');
        EasyMock.expect(readerMock.read()).andReturn((int)'b');
        EasyMock.expect(readerMock.read()).andThrow(new IOException());

        ctrl.replay();

        FicheroException ex = assertThrows(FicheroException.class,
                ()-> {
            sut.contarCaracteres(nombre);
                });

        assertEquals(nombre + " (Error al leer el archivo)", ex.getMessage());

        ctrl.verify();
    }

    @Test
    void C2_should_return_Exception_when_closing_file() throws Exception{
        String nombre = "src/test/resources/ficheroC2.txt";
        EasyMock.expect(sut.crearFileReader(nombre)).andReturn(readerMock);

        EasyMock.expect(readerMock.read()).andReturn((int)'a');
        EasyMock.expect(readerMock.read()).andReturn((int)'b');
        EasyMock.expect(readerMock.read()).andReturn((int)'c');
        EasyMock.expect(readerMock.read()).andReturn(-1);
        readerMock.close();
        EasyMock.expectLastCall().andThrow(new IOException());

        ctrl.replay();

        FicheroException ex = assertThrows(FicheroException.class,
                ()-> {
                    sut.contarCaracteres(nombre);
                });

        assertEquals(nombre + " (Error al cerrar el archivo)", ex.getMessage());

        ctrl.verify();
    }
}