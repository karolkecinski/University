# Zadanie 1

Praktyki GRASP.
Uzasadnienie rozwiązań:

## Creator

Klasa Frog towrzy instancję klasy FrogMovement. Klasa Frog "zawiera" FrogMovement (kompozycja) 
i jest niezależna od implementacji FrogMovement. Na przykład, w przyszłości może zajść konieczność zmiany implementacji poruszania się żaby w grze. Wtedy wystarczy zmienić implementację FrogMovement, natomiast sama klasa Frog pozostanie bez zmian. 

## High Cohesion

Klasy mają wyraźnie określony cel: Klasa frog jest reprezentuje instancję żaby, natomiast klasa FrogMovement implementuje 
mechanikę poruszania się żaby.

## Protected Variations

Klasa Frog implementuje konkretny interfejs: IFrog, który określa jakie metody ma ona zapewniać.

## Pure Fabrication

Sztucznie stworzyliśmy klasę FrogMovement która nie reprezentuje konceptu z dziedziny problemu, aby przypisać jej pewien zakres odpowiedzialności (ruch).