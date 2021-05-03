#include <iostream>

int main()
{
  int x = 0;
  auto id = [&]<class T>(T t) { x++; return t; };
  std::cout << id(5) << id(" xxx ") << x;
  return 0;
}
