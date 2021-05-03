// Karol Kęciński
// 315550
// PRZ
#include<cstdio>

int main()
{
    long long int a, b;

    scanf("%lld %lld", &a, &b);

    if(a > b)
    {
        int tmp = a;
        a = b;
        b = tmp;
    }

    a += 2020;
    a = a - (a % 2021);

    while(a <= b)
    {
        printf("%lld ", a);
        a += 2021;
    }

    return 0;
}