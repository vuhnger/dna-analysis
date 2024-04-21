import java.util.HashMap;

class FletteTrad implements Runnable{
    
    Monitor monitor;
    FletteTrad(
        Monitor monitor
    ){
        this.monitor = monitor;
    }

    private void flett(){

        HashMap<String, Subsekvens> h1 = monitor.taUt();
        HashMap<String, Subsekvens> h2 = monitor.taUt();
        
        if (h1 == null){return;}
        if (h2 == null){monitor.settInn(h2); return;}
        if (h1 != null && h2 != null)
        monitor.settInn(
            SubsekvensRegister.flett(h1,h2)
        );
        monitor.latch.countDown();
    }

    @Override
    public void run(){
        while(true){
            flett();
        }
    }

}

