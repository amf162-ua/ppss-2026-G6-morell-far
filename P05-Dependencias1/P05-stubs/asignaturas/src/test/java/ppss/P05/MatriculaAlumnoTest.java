package ppss.P05;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatriculaAlumnoTest {
    @Test
    void C1_compruebaMatricula_should_return_dni_00000000T_asignaturas_MD_and_FBD_and_errores(){
        //Arrange
        OperacionStub stub = new OperacionStub();
        MatriculaAlumnoTestable alumno = new MatriculaAlumnoTestable(stub);
        String dni = "00000000T";
        String[] asignaturas = {"MD", "ZZ", "FBD", "YYY", "P1"};
        String[] asignaturasEsperado = {"MD", "FBD"};
        //Act
        JustificanteMatricula justificante = alumno.validaAsignaturas(dni, asignaturas);
        //Assert
        assertAll(
                ()->assertEquals(dni, justificante.getDni()),
                ()->assertArrayEquals(asignaturasEsperado, justificante.getAsignaturas().toArray()),
                ()->assertThrows(AsignaturaIncorrectaException.class, ()->
                        stub.compruebaMatricula(dni, asignaturas[1])),
                ()->assertThrows(AsignaturaIncorrectaException.class, ()->
                        stub.compruebaMatricula(dni, asignaturas[3])),
                ()->assertThrows(AsignaturaCursadaException.class, ()->
                        stub.compruebaMatricula(dni, asignaturas[4]))
        );

    }

    @Test
    void C2_compruebaMatricula_shoud_return_dni_and_coursables_subjects(){
        //Arrange
        String dni = "00000000T";
        String[] asignaturas = {"PPSS", "ADA", "P3"};
        String[] asignaturasEsperado = {"PPSS", "ADA", "P3"};
        OperacionStub stub = new OperacionStub();
        MatriculaAlumnoTestable alumno = new MatriculaAlumnoTestable(stub);
        //Act
        JustificanteMatricula justificante = alumno.validaAsignaturas(dni, asignaturas);
        //Assert
        assertAll(
                ()->assertEquals(dni, justificante.getDni()),
                ()->assertArrayEquals(asignaturasEsperado, justificante.getAsignaturas().toArray()),
                ()->assertDoesNotThrow(()->alumno.validaAsignaturas(dni, asignaturas))
        );
    }
}