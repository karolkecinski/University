#include<cstdio>
using namespace std;
int main()
{
    int num_1, num_2;
    scanf("%d %d", &num_1, &num_2);

    if(num_1 > num_2)
    {
        int swap = num_1; 
        num_1 = num_2;
        num_2 = swap;
    }

    for(int i = num_1; i <= num_2; i++)
    {
        printf("%d \n", i);
    }
}