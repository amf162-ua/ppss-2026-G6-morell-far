package ppss.P05;

import static org.junit.jupiter.api.Assertions.*;

class ServicioStub implements IService{
    @Override
    public float consultaPrecio(TipoCoche tipo){
        return 10;
    }
}