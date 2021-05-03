#include <bits/stdc++.h>
#include <vector>

int main()
{
    unsigned long long int m, a, b;

    std::vector<std::pair<unsigned long long int, unsigned long long int>> T;

    scanf("%llu", &m);

    for (int i = 0; i < m; i++) // Wczytywanie m różnych sznurków
    {
        scanf("%llu %llu", &a, &b);

        /*
        int mv = countr_zero(T[i].first);
        
        T[i].first >> mv;
        T[i].second << mv;
        */

        while(a%2==0)
        {
            a/=2;
            b*=2;
        }

        T.push_back(std::make_pair(a, b));
    }

    std::sort(T.begin(), T.end()); // Sortujemy sznurki

    unsigned long long int licznik=T[0].second;
    long long int wynik = 0;
    int size = T.size();

    for(int i = 1; i < size; i++)
    {
        if(T[i].first == T[i-1].first)
        {
            licznik += T[i].second;
        }
        else
        {
            wynik += __builtin_popcountll(licznik);
            licznik = T[i].second;
        }
    }

    wynik += __builtin_popcountll(licznik);

    printf("%llu", wynik);

    return 0;
}

