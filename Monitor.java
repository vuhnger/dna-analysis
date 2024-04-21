import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Monitor implements Register{

    
    private Lock laas = new ReentrantLock();
    Condition notEmpty = laas.newCondition();
    private SubsekvensRegister register;
    CountDownLatch latch;
    volatile boolean ferdig;

    Monitor(int antall){
        register = new SubsekvensRegister();
        ferdig = false;
        latch = new CountDownLatch(antall);
    }

    Monitor(){
        register = new SubsekvensRegister();
    }

    @Override
    public void settInn(HashMap<String, Subsekvens> h) {
        try {
            laas.lock();
            register.settInn(h);
            notEmpty.signalAll();
        } finally{
            laas.unlock();
        }
    }

    @Override
    public HashMap<String, Subsekvens> taUt() {
        laas.lock();
        try {
            return register.taUt();
        } finally {
            laas.unlock();
        }
    }

    int size(){
        try{
            laas.lock();
            return register.hashBeholder.size();
        }finally{
            laas.unlock();
        }
    }

    void settFerdig(){
        try{
            laas.lock();
            ferdig = true;
        }finally{
            laas.unlock();
        }
    }

    boolean erFerdig(){
        return ferdig;
    }

}
