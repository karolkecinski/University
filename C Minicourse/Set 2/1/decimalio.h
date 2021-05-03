#ifndef _DECIMALIO_H
#define _DECIMALIO_H
#include <stdlib.h>

typedef unsigned char bool;

  static bool is_space_or_newline(const char c);
  static bool is_digit(const char c);

  int read_decimal();
  int trace_decimal(const int d);

#endif