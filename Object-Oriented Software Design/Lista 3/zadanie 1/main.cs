using System;
using frog;

class Program
{
    static void Main(string[] args) 
    {
        Frog john = new Frog("John", FrogSize.SMALL);
        john.swim();

        Frog beata = new Frog("Beata", FrogSize.BIG);
        beata.jump();

        Frog peter = new Frog("Peter", FrogSize.MID);
        peter.jump();
        peter.walk();
    }
}