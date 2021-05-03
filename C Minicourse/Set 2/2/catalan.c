#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "catalan.h"
#define MODULUS 1000000000

struct mem_entry memory[MODULUS];

unsigned long CatalanMem(unsigned short n)
{

    long res, first, last;

    if(n <= 1)
    {
        return 1;
    }

    res = 0;

    for(int i = 0; i<n; i++)
    {
        if(memory[i].valid == true)
        {

            first = memory[i].value;

        }else{

            first = CatalanMem(i)%MODULUS;

        }

        memory[i].value = first;
        memory[i].valid = true;

        if(memory[n-i-1].valid == true)
        {

            last = memory[n-i-1].value;

        }else{

            last = CatalanMem(n-i-1)%MODULUS;

        }

        memory[n-i-1].value = last;
        memory[n-i-1].valid = true;
        res = (res + (first * last) %MODULUS) % MODULUS;

    }

    return res;
}

unsigned long catalan(unsigned short n)
{

    if(n<=1)
    {

        return 1;

    }else{
      
        long result=0;
        
        for(int i=0; i<n; i++)
        {
            result = (result + ((catalan(i)%MODULUS)*(catalan(n-1-i)%MODULUS))%MODULUS)%MODULUS;
        }

        return result;
    }
}
