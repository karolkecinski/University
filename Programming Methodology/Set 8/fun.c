#include <stdio.h>

typedef int (*int2int)(int);

int2int twice(int2int f)
{
  int new_f(int x) { return f(f(x)); }
  return new_f;
}

int inc(int x) { return x + 1; }

int main()
{
  printf("%d", twice(inc)(1));
  return 0;
}
