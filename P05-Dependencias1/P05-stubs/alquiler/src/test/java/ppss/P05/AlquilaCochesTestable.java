package ppss.P05;

import java.time.LocalDate;

class AlquilaCochesTestable extends AlquilaCoches {
    private IService servicioStub;

    public AlquilaCochesTestable(Calendario calendarioStub, IService servicioStub) {
        this.calendario = calendarioStub;
        this.servicioStub = servicioStub;
    }

    @Override
    public Ticket calculaPrecio(TipoCoche tipo, LocalDate inicio, int ndias) throws MensajeException {
        Ticket ticket = new Ticket();
        float precioDia = servicioStub.consultaPrecio(tipo); // usa el stub
        float precioTotal = 0f;
        float porcentaje = 0.25f;
        String observaciones = "";
        for (int i = 0; i < ndias; i++) {
            LocalDate otroDia = inicio.plusDays(i);
            try {
                if (calendario.es_festivo(otroDia)) {
                    precioTotal += (1 + porcentaje) * precioDia;
                } else {
                    precioTotal += (1 - porcentaje) * precioDia;
                }
            } catch (CalendarioException e) {
                observaciones += "Error en dia: " + otroDia + "; ";
            }
        }
        if (!observaciones.isEmpty()) throw new MensajeException(observaciones);
        ticket.setPrecio_final(precioTotal);
        return ticket;
    }
}