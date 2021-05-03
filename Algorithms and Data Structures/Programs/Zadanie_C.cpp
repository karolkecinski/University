#include<cstdio>
#include<string>
#include<math.h>
#include<iostream>

using namespace std;

int STeq(int n)
{
    //Returning bits no.: 1,2,3,6,7,8,11,12,13
    int a = 0, b = 0, m = 7;
    b = n >> 12;
    a += b;
    a = a << 3;

    b = n >> 7;
    b = b & m;
    a += b;
    a = a << 3;

    b = n >> 2;
    b = b & m;
    a += b;

    return a; 
}

int NDeq(int n)
{
    //Returning bits no.: 2,3,4,7,8,9,12,13,14
    int a = 0, b = 0, m = 7;
    b = n >> 11;
    b = b & m;
    a += b;
    a = a << 3;

    b = n >> 6;
    b = b & m;
    a += b;
    a = a << 3;

    b = n >> 1;
    b = b & m;
    a += b;

    return a; 
}

int RDeq(int n)
{
    //Returning bits no.: 3,4,5,8,9,10,13,14,15
    int a = 0, b = 0, m = 7;
    b = n >> 10;
    b = b & m;
    a += b;
    a = a << 3;

    b = n >> 5;
    b = b & m;
    a += b;
    a = a << 3;

    b = n;
    b = b & m;
    a += b;

    return a; 
}

int main()
{
    int n, p, m;
    scanf("%d %d %d", &n, &p, &m);

    //Przypadki trywialne
    if(n == 1)
    {
        printf("%d", 32 % m);
        return 0;
    }
    if(n == 2)
    {
        printf("%d", 1024 % m);
        return 0;
    }
    if(p == 0)
    {
        printf("%d", (int)pow(2, 5 * n) % m);
        return 0;
    }

    //int zakazane[p][3][3];
    long long int zakazane_liczby[512];
    char line_getter[12];

    for(int a = 0; a < 512; a++)
    {
        zakazane_liczby[a] = 0;
    }

    //Wczytywanie wzorców
    int cnct = 0;

    for(int a = 0; a < p; a++)
    {
        cnct = 0;

        scanf("%c", &line_getter[11]);
        scanf("%c", &line_getter[0]);
        scanf("%c", &line_getter[3]);
        scanf("%c", &line_getter[6]);
        scanf("%c", &line_getter[9]);
        scanf("%c", &line_getter[1]);
        scanf("%c", &line_getter[4]);
        scanf("%c", &line_getter[7]);
        scanf("%c", &line_getter[10]);
        scanf("%c", &line_getter[2]);
        scanf("%c", &line_getter[5]);
        scanf("%c", &line_getter[8]);

        for(int j = 0; j < 9; j++)
        {
            if(line_getter[j] == 'x')
            {
                cnct = cnct << 1;
                cnct += 1;
            }
            else
            {
                cnct = cnct << 1;
            }
        }
        
        zakazane_liczby[cnct] = 1;
    }
    
    //Obliczanie poprawnych kształtów 5x3
    long long int shape[32768];
    int ile=0;

    for(int i = 0; i < 32768; i++)
    {
        if( zakazane_liczby[STeq(i)] == 1 ||
            zakazane_liczby[NDeq(i)] == 1 ||
            zakazane_liczby[RDeq(i)] == 1)
        {
            shape[i] = 1; //ten wzór jest zakazany
            ile++;
        } else {
            shape[i] = 0; //ten wzór jest poprawny
        }
    }

    long long int T1[1024];
    long long int T2[1024];
    for(int i = 0; i < 1024; i++)
    {
        T1[i] = 1;
        T2[i] = 0;
    }

    //Iterowanie po kolumnach i sprawdzanie kombinacji
    for(int i = 2; i < n; i++)
    {
        for(int c = 0; c < 32768; c++)
        {
            int src = c >> 5;
            int dest = c & 1023;

            if(shape[c] == 0)
            {
                T2[dest] += T1[src];
                T2[dest] = T2[dest] % m; 
            }
        }

        //swap(T1, T2);

        for(int s = 0; s < 1024; s++)
        {
            int tmp = T1[s];
            T1[s] = T2[s];
            T2[s] = tmp;
        }

        for(int q = 0; q < 1024; q++)
        {
            T2[q] = 0;
        }
    }

    long long int solution = 0;
    //Wynikiem jest suma z pól tabeli T1
    for(int i = 0; i < 1024; i++)
    {
        solution += T1[i];
        solution = solution % m;
    }

    printf("%lld\n", solution);

    return 0;
}