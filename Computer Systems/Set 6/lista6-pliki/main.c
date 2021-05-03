 #include "stdio.h"

static int global = 15210;

static void set_global(int val) {
  global = val;
}

int main(void) {

 printf("before: %d\n", global);
 set_global(15213);
 printf("after: %d\n", global);

 return 0;
}




