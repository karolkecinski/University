#include <stdio.h>
//zad 1
int f(int a, int k)
{
    return a^(1<<k);
}
int f(int a, int k)
{
    return a|(1<<k);
}

int f(int a, int k)
{
    return a&(~(1<<k));
}

////////zad 2

int f(int a, int k)
{
    return a<<k;
}

int f(int a, int k)
{
    return a>>k;
}

int f(int a, int k)
{
    int b=a;
    a = a>>k;
    a = a<<k;
    return (b-a);
}

int f(int a, int k)
{
    a = a + ((1<<k) - 1);
    return a>>k;
}

//// zad3
"""
a = a + b;
b = a - b;
a = a - b;
"""
//// zad5

int f(int a)
{
    return (a&&!(a&(a-1)));
}

//// zad 6

int f(int x, int k, int i)
{
    int a=x&(~(1<<k));
    int b=(((x&(1<<i))>>i)<<k);
    return a|b;
}

//// zad 10
"""
0 (null, NUL, \0, ^@), originally intended to be an ignored character, 
but now used by many programing languages including C to mark the end of a string

4 koniec transmisji

7 dzwonek

10 przesunięcie o jeden wiersz

12 przesunięcie o jedną stronę
"""
//// zad 11

"""
Z czasem komputery stały się na tyle wydajne, łącza na tyle szybkie, a dyski na tyle duże, że oszczędzanie na wielkości znaków przestało odgrywać znaczenie, zaczęto natomiast dostrzegać problemy związane ze stronami kodowymi:

nie jesteśmy w stanie zapisać w jednym pliku znaków z różnych stron kodowych (np. jednocześnie polskiego ł i niemieckiego ä)
aby poprawnie otworzyć plik musimy wiedzieć, w jakiej stronie kodowej jest zapisany.
W związku z tym już w 1991 opracowano Unicode - standard kodowania znaków umożliwiający zakodowanie (prawie) dowolnej ich ilości (łącznie z tak egzotycznymi dla nas i „znakochłonnymi” alfabetami jak chiński).
"""







int main()
{
    int a = 7, k;
    scanf("%d %d", &a, &k);
    printf("%d\n", f(a,k));
}