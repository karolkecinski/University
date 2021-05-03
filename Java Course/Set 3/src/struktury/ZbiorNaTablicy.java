package struktury;

public class ZbiorNaTablicy extends Zbior {

    protected Para[] zbior;
    protected int size;
    protected int numberOfElements = 0;

    public ZbiorNaTablicy(int size) throws Exception
    {
        if(size < 2)
            throw new Exception("Podana wielkość tablicy jest zbyt mała.");

        this.zbior = new Para[size];
        this.size = size;
    }

    public ZbiorNaTablicy()
    {
        this.zbior = new Para[2];
        this.size = 2;
    }

    /*
     * * * * * *
     * Methods *
     * * * * * *
     */

    public Para szukaj(String k) throws Exception
    {
        for(int i = 0; i < this.numberOfElements; i++) {
            if(this.zbior[i].klucz.equals(k))
                return this.zbior[i];
        }

        throw new Exception("Nie znaleziono pary o podanym kluczu.");
    }

    public void wstaw(Para p) throws Exception
    {
        try
        {
            this.szukaj(p.klucz);
        } catch (Exception e) {

            this.zbior[this.numberOfElements] = p;
            this.numberOfElements++;
            return;
        }
        throw new Exception("Para o podanym kluczu już istnieje");
    }

    public void poprzesuwaj(int n)
    {
        for(int i = n; i < this.numberOfElements - 1; i++)
        {
            this.zbior[i] = this.zbior[i+1];
        }
    }

    public void usun(String k)
    {
        for(int i = 0; i < this.numberOfElements; i++) {
            if(this.zbior[i].klucz.equals(k))
            {
                this.poprzesuwaj(i);   // Chcąc usunąć C z ABCDE... robimy przesunięcie C <- D, D <-E, ...
                numberOfElements--;
                break;
            }
        }
    }

    public double czytaj(String k) throws Exception
    {
        for(int i = 0; i < this.numberOfElements; i++) {
            if(this.zbior[i].klucz.equals(k))
                return this.zbior[i].get();
        }

        throw new Exception("Nie znaleziono pary o podanym kluczu.");
    }

    public void ustaw(Para p) throws Exception
    {
        for(int i = 0; i < this.numberOfElements; i++) {
            if(this.zbior[i].klucz.equals(p.klucz)) {
                this.zbior[i] = p;
                return;
            }
        }

        this.wstaw(p);
    }

    public void czysc()
    {
        this.zbior = new Para[size];
        this.numberOfElements = 0;
    }

    public int ile()
    {
        return this.numberOfElements;
    }

}
