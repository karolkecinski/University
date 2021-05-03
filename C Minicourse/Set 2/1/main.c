#include <stdio.h>
#include "decimalio.h"
int main(void)
{
  
    int wynik=1;
    int l = read_decimal();

    for(int i=0; i<l; i++)
    {
        int wynik = i*i;
        trace_decimal(wynik);
    }

    return 0;
}
