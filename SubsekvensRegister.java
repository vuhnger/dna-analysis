import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

interface Register{
    void settInn(HashMap<String, Subsekvens> h);
    HashMap<String, Subsekvens> taUt();
}

class SubsekvensRegister implements Register{

    ArrayList<HashMap<String, Subsekvens>> hashBeholder;

    SubsekvensRegister(){
        hashBeholder = new ArrayList<>();
    }

    static HashMap<String, Subsekvens> flett(
        HashMap<String, Subsekvens> h1,
        HashMap<String, Subsekvens> h2
    ){
        HashMap<String, Subsekvens> resultat = new HashMap<>();

        resultat.putAll(h1);

        for (String s : h2.keySet()){
            if (resultat.containsKey(s)){
                resultat.put(s, new Subsekvens(s, resultat.get(s).antall + h2.get(s).antall));
                assert resultat.get(s).antall + h2.get(s).antall > 0;
            }else{
                resultat.put(
                    s, new Subsekvens(s, h2.get(s).antall)
                );
            }
        }
        return resultat;
    }

    static void finnMaksFrekvens(
        HashMap<String, Subsekvens> h
    ){
        if (h == null){System.out.print("Ingen frekvenstabell å sjekke. "); return;}
        int frekvens = 0;
        String maks = "";
        for (String s : h.keySet()){
            if (h.get(s).antall > frekvens){
                maks = s;
                frekvens = h.get(s).antall;
            }
        }
        System.out.println(maks + ":" + frekvens);
    }

    static HashMap<String, Subsekvens> lesFil(File fil){
        HashMap<String, Subsekvens> personDna = new HashMap<>();
        try {

            BufferedReader leser = new BufferedReader(new FileReader(fil));

            String linje = "";
            
            while ((linje = leser.readLine()) != null){
                if (linje.length() < 3){continue;}
                if (linje == "amino_acid"){continue;}
                linje = linje.strip();
                for (int i = 0; i < linje.length() - 2; i++){
                    String subsekvens = linje.substring(i, i + 3);
                    if (personDna.containsKey(linje)){continue;}
                    personDna.put(subsekvens, new Subsekvens(subsekvens, 1));
                }
            }
            leser.close();
            return personDna.isEmpty() ? null : personDna;
        }
        catch (FileNotFoundException e) {
            System.err.println("Fant ikke filen: " + fil.getName());
        }
        catch (IOException e){
            System.err.println("Feil med leser: " + e.getMessage());            
        }
        return null;
    }

    @Override
    public void settInn(HashMap<String, Subsekvens> personDna){
        hashBeholder.add(personDna);
    }

    @Override
    public HashMap<String, Subsekvens> taUt(){
        return hashBeholder.isEmpty() ? null : hashBeholder.remove(0);
    }

}