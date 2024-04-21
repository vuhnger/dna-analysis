import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
        
    }

    public static void main(String[] args) {
        //testFlettMappe();
    }
}
