package llamadas;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CalendarioStub extends Calendario{
    private int hora;

    public CalendarioStub(int hora){
        this.hora = hora;
    }

    @Override
    public int getHoraActual(){
        return hora;
    }
}