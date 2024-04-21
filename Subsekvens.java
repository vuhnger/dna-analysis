
class Subsekvens{

    final String subsekvens;
    int antall;

    Subsekvens(String subsekvens, int antall){
        this.subsekvens = subsekvens;
        this.antall = antall;
    }

    @Override
    public String toString(){
        return "(" + subsekvens + "," + antall + ")";
    }

}