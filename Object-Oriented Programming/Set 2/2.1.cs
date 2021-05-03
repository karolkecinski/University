class IntStream
{
    int number=0;
    public int next()
    {
        number++;
        return (number-1);
    }

    public bool eos()
    {
        if(number==int.MaxValue))
            return true;
        return false;
    }

    public void reset()
    {
        number = 0;
    }
}

bool isprime(int n)
{
	if(n<2)
		return false;
		
	for(int i=2;i*i<=n;i++)
		if(n%i==0)
			return false;
	return true;
}

class PrimeStream : IntStream
{
    int primes;
    int next()
    {
        int p=primes;
        while (isprime(p==0) and !this.eos())
        {
            p++;
        }

        return p;
    }
}

class RandomStream : IntStream
{
    int next()
    {
        int number;

        srand(time(NULL));
        number = rand()%1000000;
        
        return number; 
    }
}