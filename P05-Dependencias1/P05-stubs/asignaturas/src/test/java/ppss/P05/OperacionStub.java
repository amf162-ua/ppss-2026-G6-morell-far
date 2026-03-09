package ppss.P05;

class OperacionStub extends Operacion{
    @Override
    public void compruebaMatricula(String dni, String asignatura) throws AsignaturaIncorrectaException, AsignaturaCursadaException{
        String[] cursadas = {"P1", "FC", "FFI"};
        String[] noExisten = {"ZZ", "YYY"};

        for(String c : cursadas){
            if(c.equals(asignatura)) throw new AsignaturaCursadaException("Ya cursada");
        }

        for(String n : noExisten){
            if(n.equals(asignatura)) throw new AsignaturaIncorrectaException("No existe");
        }
    }
}