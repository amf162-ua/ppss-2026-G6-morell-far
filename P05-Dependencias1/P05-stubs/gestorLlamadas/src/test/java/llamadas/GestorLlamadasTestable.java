package llamadas;

class GestorLlamadasTestable extends GestorLlamadas{
    private Calendario calendario;

    public GestorLlamadasTestable(Calendario calendario){
        this.calendario = calendario;
    }

    @Override
    public Calendario getCalendario(){
        return calendario;
    }
}