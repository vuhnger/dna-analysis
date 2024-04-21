import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class Monitor implements Register{

    
    private Lock laas = new ReentrantLock();
    private SubsekvensRegister register;

    Monitor(){
        register = new SubsekvensRegister();
    }

    @Override
    public void settInn(HashMap<String, Subsekvens> h) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'settInn'");
    }

    @Override
    public HashMap<String, Subsekvens> taUt() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'taUt'");
    }



}
