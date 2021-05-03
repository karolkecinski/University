#include <stdio.h>
#include <stdlib.h>
typedef int my_type;
my_type max(my_type array[], size_t arr_size)
{
    my_type maximum = array[0];

    for(int i=1; i<arr_size; i++)
    {
        maximum=geq(maximum, array[i]);
    }

    return maximum;
}
int main()
{
    printf("Hello world!\n");
    return 0;
}
