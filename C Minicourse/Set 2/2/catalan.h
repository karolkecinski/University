#ifndef _CATALAN_H
#define _CATALAN_H
#include <stdlib.h>
#include <stdbool.h>

#define MODULUS 1000000000

struct mem_entry{
    unsigned long value;
    bool valid;
};

unsigned long catalan(unsigned short n);

#endif