#include <stdio.h>
#include <assert.h>

char* strcat(char *destination, const char *source)
{
    size_t i = 0, j = 0;

    while(*(destination + i) != '\0') 
    {
        i++;
    }

    while(*(source + j) != '\0')
    {
        *(destination + i) = *(source + j); // *(destination + i) <=> destination[i]
        i++;
        j++;
    }

    *(destination + i) = '\0'; // Zakonczenie znakiem konca linii

    return destination;
}

char* strncat(char *destination, const char *source, size_t num )
{
    size_t i = 0, j = 0;

    while(*(destination + i) != '\0') 
    {
        i++;
    }

    while(num > 0 && *(source + j) != '\0')
    {
        *(destination + i) = *(source + j); // *(destination + i) <=> destination[i]
        num--;
        i++;
        j++;
    }

    *(destination + i) = '\0'; // Zakonczenie znakiem konca linii

    return destination;
}

int strcmp(const char * str1, const char * str2 )
{
    for(int i = 0; *(str1 + i) != '\0' && *(str2 + i) != '\0'; i++)
    {
        if(*(str1 + i) != *(str2 + i))
            return (*(str1 + i) - *(str2 + i));
    }

    return 0;
}

int strncmp( const char * str1, const char * str2, size_t num )
{
    for(int i = 0; *(str1 + i) != '\0' && *(str2 + i) != '\0' && num > 0; i++)
    {
        if(*(str1 + i) != *(str2 + i))
            return (*(str1 + i) - *(str2 + i));
        
        num--;
    }
    
    return 0;
}

int main() 
{
    char ciag_znakow[50] = "Ala ";
    strcat (ciag_znakow,"ma ");
    strcat (ciag_znakow,"Kota");
    puts (ciag_znakow);
    
    char ciag_znakow2[50] = "Konkatynacja ";
    strncat (ciag_znakow2,"sie ", 5);
    strncat (ciag_znakow2,"powiodla", 9);
    puts (ciag_znakow2);

    char s1[8] = "ABCDEFGH";
    char s2[8] = "ABCDAXXX";

    printf("Strcmp: \n");
    printf("%d\n",strcmp(s1, s2));
    printf("%d\n",strcmp(s2, s1));
    printf("%d\n",strcmp(s1, s1));
    
    printf("Strncmp: \n");
    printf("%d\n",strncmp(s1, s2, 8));
    printf("%d\n",strncmp(s2, s1, 8));
    printf("%d\n",strncmp(s1, s1, 8));

    return 0;
}