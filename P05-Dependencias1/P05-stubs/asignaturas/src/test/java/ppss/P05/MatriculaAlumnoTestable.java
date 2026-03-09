package ppss.P05;

class MatriculaAlumnoTestable extends MatriculaAlumno{
    private Operacion operacion;

    public MatriculaAlumnoTestable(Operacion operacion){
        this.operacion = operacion;
    }

    @Override
    protected Operacion getOperacion(){
        return operacion;
    }
}