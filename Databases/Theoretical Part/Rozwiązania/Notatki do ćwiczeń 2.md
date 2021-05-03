# Bazy danych - lista 2


### Zadanie 2
Mamy relacje *R(A, B, C)* oraz *S(X, Z)*.
Chcemy zapytanie takie, że zwróci pusty wynik wtedy i tylko wtedy, gdy atrybut *Z* relacji *S* spełnia własności klucza obcego wskazującego na atrybut *A* relacji *R*. 
$$\{z | (\exists{x})(S(x, z)) \wedge \neg (\exists{b, c})(R(z, b, c)) \}$$

### Zadanie 3

![](https://imgur.com/8pqilDO.jpg)
Spośród wszystkich *a* występujących w *R* zwraca to największe.
Nie jest zależne od dziedziny.
Formuła w algebrze relacji:

$$R \setminus \pi_{a,b}(\sigma_{a<a1}(R \times \rho_{R(a1,b1)}R))$$

![](https://imgur.com/XVlrTTH.jpg)
Zwraca takie pary *(a, b)*, że dla każdego *c* znajdującego się w relacji *T*, *c* jest w relacji w parze z *a* lub *b* lub relacja *T* jest pusta.
Jest zależne od dziedziny, przykład:
$$T(b, c) = \{1, 1\}$$
$a)$ Dla
$$D_a = \{1\} , D_b = \{1\}, D_c = \{1\}$$
Wynikiem jest para $\{1, 1\}$.

$b)$ Dla
$$D_a = \{1\}, D_b = \{1, 2\}, D_c = \{1\}$$
Wynikami są pary $\{1, 1\}, \{2, 1\}$.

### Zadanie 4
![](https://i.imgur.com/g1IdXnV.png)

Wypisz osoby bywające tylko w tych barach, w których podaje się (przynajmniej) jeden z ich ulubionych soków.

![](https://i.imgur.com/cmjApJb.png)

1. Poprawne - nie istnieje taki bar, że ta osoba do niego chodzi i nie lubi wszystkich soków, które tam podają. 
2. Niepoprawne - nie istnieje taki bar, że ta osoba do niego chodzi i lubi wszystkie soki podawane w tym barze.
3. Poprawne - dla każdego baru, jeśli ta osoba do niego chodzi, to jest podawany w tym barze przynajmniej jeden sok, który ta osoba lubi.
4. Niepoprawne - osoba chodzi do wszystkich barów i w każdym z nich jest przynajmniej jeden sok, który ta osoba lubi.

### Zadanie 5
![](https://i.imgur.com/g1IdXnV.png)

Podaj osoby chodzące tylko do jednego baru.

![](https://i.imgur.com/X5mXw4Z.png)

1. Niepoprawne - istnieje taki bar, w którym bywa osoba i nie istnieje taki bar, w którym bywa osoba.nie istnieje taki bar, w którym bywa osoba.
2. Niepoprawne - osoby, które chodzą do przynajmniej jednego baru.
3. Poprawne - istniej bar, w którym bywa osoba i nie istnieje inny bar, że bywa tam osoba.
4. Poprawne - to samo, co wyżej.

### Zadanie 6

![](https://i.imgur.com/adFSqxu.png)

1. Podaj dane aktorów (pseudonim, imię, nazwisko, rok urodzenia, narodowość), którzy pojawili się w filmach produkowanych tylko w jednym roku (powiedzmy, że są to gwiazdy jednego sezonu).

$$ \{aktor | aktor \in A \wedge (\forall film, film2) ((
\exists p, g, p2, g2)(R(aktor.pseudo, film.idf, p, g) \wedge R(aktor.pseudo, film2.idf, p2, g2))) \Rightarrow (film.rokProd = film2.rokProd) \} $$

$$ \{aktor | aktor \in A \wedge (\forall film, film2 \in F)((\exists r, r2 \in R)(r.pseudo = aktor.pseudo \wedge r2.pseudo = aktor.pseudo \wedge r.idf = film.idf \wedge r2.idf = film2.idf) \Rightarrow (film.rokProd = film2.rokProd))) \} $$

2. Podaj pełne krotki filmów, które są najnowszymi filmami reżyserów.

$$ \{ film | film \in F \wedge \neg (\exists film2 \in F) (film.rezyser = film2.rezyser \wedge film.rokProd < film2.rokProd) \}$$

3. Dla każdego filmu znajdź aktora, który dostał najwyższą gażę w tym filmie (został najlepiej opłacony z obsady filmu). W relacji wynikowej podaj pseudonim aktora, idf oraz gażę.

$$ \{ pseudo, idf, gaza | ((\exists p)R(pseudo, idf, p, gaza)) \wedge (\exists film)(film.idf = idf \wedge (\forall r \in R)(r.idf = idf \Rightarrow r.gaza <= gaza)) \} $$

4. Podaj pełne krotki aktorów, którzy nigdy nie obniżyli swojej minimalnej gaży (w późniejszych latach mogła ona najwyżej rosnąć). Na wynik nie wpływają lata, w których aktor nie podał minimalnej gaży.

$$ \{ aktor | aktor \in A \wedge (\forall gaza \in M)((gaza.pseudo = aktor.pseudo) \wedge ((\forall gaza2 \in M)((gaza.pseudo = gaza2.pseudo \wedge gaza.minGaza < gaza2.minGaza) \Rightarrow (gaza.rok < gaza2.rok))))\} $$