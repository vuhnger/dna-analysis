import java.io.File;

class LeseTrad implements Runnable{
    
    Monitor monitor;
    File fil;
    LeseTrad(
        Monitor monitor,
        File fil
    ){
        this.monitor = monitor;
        this.fil = fil;
    }

    @Override 
    public void run(){
        monitor.settInn(
            SubsekvensRegister.lesFil(fil)
        );
    }
}