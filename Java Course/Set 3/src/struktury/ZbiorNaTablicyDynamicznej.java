package struktury;

public class ZbiorNaTablicyDynamicznej extends ZbiorNaTablicy {

    public ZbiorNaTablicyDynamicznej()
    {
        super();
    }

    @Override
    public void wstaw(Para p) throws Exception
    {
        try
        {
            this.szukaj(p.klucz);
        } catch (Exception e) {

            if(numberOfElements == size)
            {
                Para[] nowyZbior = new Para[2*size];

                for(int i = 0; i < size; i++)
                {
                    nowyZbior[i] = zbior[i];
                }
                size*=2;

                this.zbior = nowyZbior;
            }

            this.zbior[numberOfElements]=p;
            numberOfElements++;
            return;
        }

        throw new Exception("Para o podanym kluczu już istnieje");
    }

    /*
    @Override
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
    */

    @Override
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

        this.pomniejsz();
    }

    public void pomniejsz()
    {
        if(4 * this.numberOfElements <  this.size)
        {
            Para[] nowyZbior = new Para[this.size / 2];

            for(int i = 0; i < this.numberOfElements; i++)
            {
                nowyZbior[i] = this.zbior[i];
            }
            this.size /= 2;

            this.zbior = nowyZbior;
        }
    }

    @Override
    public void czysc()
    {
        this.size = 2;
        this.zbior = new Para[size];
        this.numberOfElements = 0;
    }
}
