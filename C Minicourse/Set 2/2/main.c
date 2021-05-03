#include <stdio.h>
#include <stdlib.h>
#include "catalan.h"

struct mem_entry memory[MODULUS];

int main()
{
    int i;

    for(i = 1; i < 25; i++)
    {
        printf("%d\n", CatalanMem(i));
    }

    return 0;
}
