import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestSubsekvensRegister {

    static void testInnlesning(){
        SubsekvensRegister r = new SubsekvensRegister();
        File testFil = new File("testdata/TestData/fil1.csv");
        r.settInn(SubsekvensRegister.lesFil(testFil));
        for (HashMap<String, Subsekvens> h : r.hashBeholder){
            for (String s : h.keySet()){
                System.out.println(s);
            }
        }
    }

    static void testMappeInnlesning(){
        SubsekvensRegister r = new SubsekvensRegister();
        Path sti = Paths.get("testdata/TestData");
        try (Stream<Path> stream = Files.list(sti)) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the directory: " + e.getMessage());
        }
    }

    static void testMappeProsessering(){
        SubsekvensRegister r = new SubsekvensRegister();
        Path sti = Paths.get("data/Data");
        try (Stream<Path> stream = Files.list(sti)) {
            stream.map(Path::toFile).forEach(
                fil -> {
                    r.settInn(SubsekvensRegister.lesFil(fil));
                }
            );
        } catch (IOException e) {
            System.err.println("Error reading the directory: " + e.getMessage());
        }
        System.out.println(r.hashBeholder.size());
    }

    static void testFlettMappe(){
        SubsekvensRegister r = new SubsekvensRegister();
        Path sti = Paths.get("testdatalike/TestDataLike");
        try (Stream<Path> stream = Files.list(sti)) {
            stream.map(Path::toFile).forEach(
                fil -> {
                    r.settInn(SubsekvensRegister.lesFil(fil));
                }
            );
        } catch (IOException e) {
            System.err.println("Error reading the directory: " + e.getMessage());
        }
        
        while (r.hashBeholder.size() > 2){
            r.settInn(
                SubsekvensRegister.flett(
                    r.taUt(),
                    r.taUt()
                )
            );
        }
        if (r.hashBeholder.size() == 2){
            r.settInn(
                SubsekvensRegister.flett(
                    r.taUt(),
                    r.taUt()
                )
            );
        }
        if (r.hashBeholder.size() == 1){
            SubsekvensRegister.finnMaksFrekvens(r.taUt());
        }
    }

    static void testLeseTråd(){
        String testDataLike = "testdatalike/TestDataLike";
        String testData = "data/Data";
        Monitor monitor = new Monitor();
        Path sti = Paths.get(testData);
        List<File> filer = Collections.emptyList();
        try {
            filer = Files.list(sti)
            .map(Path::toFile)
            .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread[] lesere = new Thread[filer.size()];
        for (int i = 0; i < filer.size(); i++){
            lesere[i] = new Thread(
                new LeseTrad(monitor, filer.get(i))
            );
            lesere[i].start();
        }

        for (Thread thread : lesere) {
            try {
                thread.join();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // Handle thread interruption
                System.err.println("Thread was interrupted: " + ie.getMessage());
            }
        }

        while (monitor.size() > 2) {
            monitor.settInn(
                SubsekvensRegister.flett(
                    monitor.taUt(),
                    monitor.taUt()
                )
            );
        }

        if (monitor.size() == 2){
            monitor.settInn(
                SubsekvensRegister.flett(
                    monitor.taUt(),
                    monitor.taUt()
                )
            );
        }

        if (monitor.size() == 1){
            SubsekvensRegister.finnMaksFrekvens(
                monitor.taUt()    
            );
        }
    }

    static void testFletteTrad(){
        String testDataLike = "testdatalike/TestDataLike";
        String testData = "data/Data";
        Path sti = Paths.get(testDataLike);
        List<File> filer = Collections.emptyList();
        try {
            filer = Files.list(sti)
            .map(Path::toFile)
            .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Monitor monitor = new Monitor(
            filer.size() / 2
        );
        Thread[] lesere = new Thread[filer.size()];
        for (int i = 0; i < filer.size(); i++){
            lesere[i] = new Thread(
                new LeseTrad(monitor, filer.get(i))
            );
            lesere[i].start();
        }

        for (Thread thread : lesere) {
            try {
                thread.join();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // Handle thread interruption
                System.err.println("Thread was interrupted: " + ie.getMessage());
            }
        }

        Thread[] flettere = new Thread[8];
        for (int i = 0; i < flettere.length; i++){
            flettere[i] = new Thread(
                new FletteTrad(monitor)
            );
            flettere[i].start();
        }

        try {
            monitor.latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SubsekvensRegister.finnMaksFrekvens(
            monitor.taUt()
        );

    }

    public static void main(String[] args) {
        //testFlettMappe();
        //testLeseTråd();
        testFletteTrad();
    }
}
