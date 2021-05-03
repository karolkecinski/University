#include <stdio.h>
#include <stdlib.h>
int max(int array[], size_t arr_size)
{
    int maximum = array[0];

    for(int i=1; i<arr_size; i++)
    {
        if(array[i]>maximum)
            maximum=array[i];
    }

    return maximum;
}
int main()
{
    int size;
    scanf("%d", &size);
    int a[size];

    for(int i = 0; i < size; i++)
    {
        scanf("%d", &a[i]);
    }

    printf("%d", max(a, size));

    return 0;
}
