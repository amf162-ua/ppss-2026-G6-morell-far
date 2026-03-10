package ppss.P05;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CalendarioStub extends Calendario{
    private Map<LocalDate, Boolean> mapa = new HashMap<>();
    private Set<LocalDate> excepciones = new HashSet<>();

    public CalendarioStub(){
        LocalDate fechaInicioC1 = LocalDate.of(2024, 5, 18);
        LocalDate fechaInicioC2 = LocalDate.of(2024, 6, 19);
        LocalDate fechaInicioC3 = LocalDate.of(2024, 4, 17);

        for (int i = 0; i < 10; i++){
            mapa.put(fechaInicioC1.plusDays(i), false);
        }

        for (int i = 0; i < 7; i++){
            LocalDate fecha = fechaInicioC2.plusDays(i);

            if(fecha.getDayOfMonth()==20 || fecha.getDayOfMonth() == 24){
                mapa.put(fecha, true);
            }else {
                mapa.put(fecha, false);
            }
        }

        for (int i = 0; i < 8; i++){
            LocalDate fecha = fechaInicioC3.plusDays(i);

            if(fecha.getDayOfMonth()==21 || fecha.getDayOfMonth() == 22 || fecha.getDayOfMonth() == 18){
                excepciones.add(fecha);
            }else {
                mapa.put(fecha, false);
            }
        }
    }

    @Override
    public boolean es_festivo(LocalDate dia) throws CalendarioException {
        if(excepciones.contains(dia)){
            throw new CalendarioException();
        }
        return mapa.getOrDefault(dia, false);
    }
}