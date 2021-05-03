#include <stdio.h>
#include "decimalio.h"

typedef unsigned char bool;

static bool is_digit(const char c){
    return '0' <= c && c <= '9';
}

static bool is_space_or_newline(const char c){
    return c == ' ' || c == '\n';
}

int read_decimal(){
    int d = 0;
    char c;
    do
        c = getchar();
    while( is_space_or_newline(c) );
    do{
        d *= 10;
        d += c - '0';
        c = getchar();
    }while( is_digit(c) );

    ungetc(c, stdin);
    return d;
}

int trace_decimal(const int d){
    int b = 1;
    while(b * 10 <= d)
        b *= 10;
    while(b > 0){
        putchar( (d/b)%10 + '0' );
        b /= 10;
    }
    putchar('\n');
    return d;
}
