#include <stdio.h>
#include <stdlib.h>
int max2(int array[][77], size_t side_size)
{
    int maximum = array[0][0];

    for(int i=0; i<side_size; i++)
    {
        for(int j=0; j<77; j++)
        {
            if(maximum < array[i][j])
                maximum = array[i][j];
        }
    }

    return maximum;
}
int main()
{
    int size;
    scanf("%d", &size);
    int a[size][77];

    for(int i = 0; i < size; i++)
    {
        for(int j=0; j<77; j++)
        {
            scanf("%d", &a[i][j]);
        }
    }

    printf("%d", max2(a, size));
    return 0;
}
