package ppss.practica3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    private FicheroTexto sut;
    @BeforeEach
    void setUp() {
        sut = new FicheroTexto();
    }

    @Test
    void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist() {
        //Arrange
        String nombre = "ficheroC1.txt";
        //Act
        FicheroException exception = assertThrows(
                FicheroException.class,
                () -> sut.contarCaracteres(nombre)
        );

        //Assert
        assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", exception.getMessage());
    }

    @Test
    void C2_contarCaracteres_should_return_4_when_file_has_4_chars() throws FicheroException{
        //Arrange
        String nombre = getClass().getClassLoader().getResource("ficheroCorrecto.txt").getPath();
        int esperado = 4;

        //Act
        int resultado = sut.contarCaracteres(nombre);

        //Assert
        assertEquals(esperado, resultado);
    }
    @Test
    @Tag("excluido")
    void C3_contarCaracteres_should_return_Exception_when_file_cannot_be_read(){
        fail();
    }

    @Test
    @Tag("excluido")
    void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed(){
        fail();
    }
}