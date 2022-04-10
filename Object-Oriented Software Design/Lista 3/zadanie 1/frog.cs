using System;

namespace frog
{
    public interface IFrog {
        public void swim();
        public void walk();
        public void jump();
    }
    public enum FrogSize {
        SMALL,
        MID,
        BIG
    }

    public class FrogMovement 
    {

        public String speed;

        public FrogMovement(FrogSize size) {
            switch(size) {
                case FrogSize.SMALL: {
                    this.speed = "slowly";
                    break;
                }
                case FrogSize.MID: {
                    this.speed = "moderately";
                    break;
                }
                case FrogSize.BIG: {
                    this.speed = "fast";
                    break;
                }
                default: {
                    this.speed = "NULL";
                    break;
                }
            }
        }

        public void walk(string name) { 
            Console.WriteLine("Frog " + name + " walks " + speed + ".");
        }
        public void swim(string name) { 
            Console.WriteLine("Frog " + name + " swims " + speed + ".");
        }
        public void jump(string name) { 
            Console.WriteLine("Frog " + name + " jumps " + speed + ".");
        }
    }
    
    public class Frog : IFrog
    {
        FrogSize fs;
        FrogMovement fm;
        string name;
        
        public Frog(string name, FrogSize size) {
            this.name = name;
            this.fs = size;
            this.fm = new FrogMovement(size);
        }

        public void walk() {
            fm.walk(name);
        }

        public void swim() {
            fm.swim(name);
        }

        public void jump() {
            fm.jump(name);
        }
    }
}