package ppss.P05;

import java.time.LocalDate;

class AlquilaCochesTestable extends AlquilaCoches {
    @Override
    protected IService createServicio() {
        return new ServicioStub();  // factoría local devuelve el stub
    }

    public AlquilaCochesTestable(Calendario calendarioStub) {
        this.calendario = calendarioStub;  // sustituimos el calendario real
    }
}