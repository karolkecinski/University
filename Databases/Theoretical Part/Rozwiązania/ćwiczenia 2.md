# Bazy danych - ćwiczenia 2021

## Ściągawka
Można używać LaTeX: \$\polecenie\$
leftarrow \leftarrow $\leftarrow$
select \sigma $\sigma$
project \Pi $\Pi$
inner join \bowtie $\bowtie$
cross product \times $\times$
rename \rho $\rho$
klamry { } $\{ \}$

## 9 marca

### Deklaracje

:::danger
|                      |  1  |  2  | 3a  | 3b  | 3c  | 3d  |  4  |
| -------------------: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|     Wojciech Adamiec |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
|     Karol Cieślawski |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
| Przemysław Hoszowski |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
|       Karol Kęciński |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
|        Dominik Komła |  X  |  X  |  X  |  X  |  X  |     |     |
|    Grzegorz Maliniak |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
|   Michał Mikołajczyk |     |  X  |  X  |  X  |  X  |  X  |  X  |
|    Barbara Moczulska |  X  |  X  |  X  |     |  X  |     |     |
|         Antoni Nowak |     |     |  X  |  X  |     |  X  |  X  |
|       Łukasz Orawiec |  X  |  X  |  X  |  X  |  X  |  X  |     |
|       Natalia Piasta |     |  X  |  X  |  X  |  X  |  X  |  X  |
|         Łukasz Pluta |  X  |  X  |  X  |  X  |  X  |  X  |     |
|   Przemysław Stasiuk |     |  X  |  X  |  X  |  X  |  X  |  X  |
|     Dominik Trautman |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
|        Jan Wańkowicz |  X  |  X  |  X  |  X  |  X  |  X  |     |
|     Michał Wójtowicz |     |  X  |  X  |  X  |  X  |  X  |  X  |
|         Nikola Wrona |  X  |  X  |  X  |  X  |  X  |  X  |  X  |
|           Adam Zyzik |  X  |  X  |  X  |  X  |  X  |  X  |     |
:::

### Rozwiązania

#### 1
Łukasz Orawiec

Operator $op$ jest monotoniczny, jeśli 

$R \subseteq R' \ \Rightarrow \ op(R) \subseteq op(R')$

dla dowolnych relacji $R$, $R'$.

<br/>

Operatory monotoniczne:
* $\pi_\alpha(R), \ \rho_S(R)$ -- zawsze zwracają relację o tej samej liczbie krotek co $R$

* $\sigma_F(R)$ - po dodaniu krotek do $R$ wszystkie krotki poprzednio spełniające warunek $F$ dalej go spełniają

* $R \times S$ -- po dodaniu krotek do $R$ lub $S$, w ich iloczynie kartezjańskim znajdą się nowe krotki, a stare się nie zmienią

* $R \cup S$ -- tak jak wyżej

<br/>

Operator niemonotoniczny:
* $R \setminus S$ -- po dodaniu nowych krotek do $S$ w różnicy tych relacji liczba krotek może się zmniejszyć

<br/>

Złożenia operatorów monotonicznych są monotonicznymi zapytaniami, więc operatora $\setminus$ nie można wyrazić za pomocą algebry relacji z operatorami $\pi, \sigma, \rho, \times, \cup$.


#### 2
Adam Zyzik

Weźmy $X = Z = \{1\}, Y = \emptyset$.

Wtedy $(X \times \rho_{Y(A_Y)}(Y) \times \rho_{Z(A_Z)}(Z)) = \emptyset$, a $X \cap (Y \cup Z) = \{1\}$.

Wyrażenie możemy zapisać tak: $X \setminus ((X \setminus Y) \setminus Z)$

#### 3a
Barbara Moczulska

$G_{count_{sok}(bar)}(\pi _{bar,sok}(B \Join P))$

$(\pi _{bar,sok}(B \Join P))$ zwraca relację, zawierającą takie listy barów i soków, że w barze podaje się sok oraz chodzi tam jakaś osoba.

$G_{count_{sok}(bar)}(\pi _{bar,sok}(B \Join P))$ zwraca relację: bar do którego chodzi jakaś osoba, n sokow podawanych w tym barze


#### 3b
Michał Mikołajczyk
:::info
Treść zadania:
![](https://i.imgur.com/QRgas9H.png)
:::
$L$ -- **osoba** lubi **sok**
$P$ -- **sok** jest podawany w **barze** w **cenie**
$L \Join P$ -- złączenie po **sok** -- krotki (osoba, sok, bar, cena)
$\pi_{osoba,bar}\ \sigma_{n \geq 5}\ G_{count_{sok}(osoba,bar)}(L \Join P)$

#### 3c
Karol Kęciński

![](https://i.imgur.com/eT1TEo7.png)

$\pi_{osoba, sok, cena}(P \Join B)$ <- Operator $\pi$ zwraca Krotki (osoba, sok, cena), z osobób osoba, które bywają w barze w którym podają sok sok z ceną cena. Nie interesuje nas, w którym konkretnym barze jest podawany ten sok, tylko jego minimalna cena spośród wszystkich barów w których bywa dana osoba. Operator $\pi$ eliminuje też powtórzenia.

Dla tych samych osób i soków ceny mogą być różne, np. (o1, s1, c1), (o1, s1, c2), Więc chcemy zwrócić krotki z minimalną ceną (o1, s1, min(cena)). 

Skorzystamy z operatora agregacji i grupowania $G_{min_{cena}(osoba, sok)}$.

Ostateczne zapytanie wygląda tak:

$G_{min_{cena}(osoba, sok)}(\pi_{osoba, sok, cena}(P \Join B))$

#### 3d
Nikola Wrona

![](https://i.imgur.com/spHJCtg.png)


$\pi_{osoba, sok, bar}(P \Join G_{min_{cena}(osoba, sok)}(B \Join P))$

$B(osoba, bar)$ - osoba bywa w bar
$P(sok, bar, cena)$ - w bar podają sok w cenie cena za porcję
$B \Join P$ - zwraca tabelę $(osoba, bar, sok, cena)$
$G_{min_{cena}(osoba, sok)}(B \Join P)$ - zwraca $(osoba, sok, cena)$
Następnie wykonujemy złączenie naturalne z $P$, dostajemy $(osoba, sok, bar, cena)$ i używając rzutu wypisujemy krotki $(osoba, sok, bar)$



#### 4
a. Dla każdego filmu między 1960 a 1970 rokiem wypisz jego tytuł, nazwisko reżysera i gatunek (genre).
Michał Wójtowicz
```
M = (rho id->movie_id movies) ⨝ movies_directors
  ⨝ movies_genres ⨝ (rho id->director_id directors)
M2 = pi name, last_name, genre, year M
M3 = sigma year ≥ 1960 ∧ year ≤ 1970 M2
M4 = pi name, last_name, genre M3
M4
```

b. Wypisz imiona i nazwiska aktorów, którzy dokładnie raz zagrali w jakimś filmie Quentina Tarantino.
Karol Cieślawski
```
X = σ xdir=78273 (π xdir (ρ xdir ← director_id (movies_directors)) ⨝ (ρ xid←actor_id, xmov_id←movie_id, xr←role (roles)))
Y = σ ydir=78273 (π ydir (ρ ydir ← director_id (movies_directors)) ⨝ (ρ yid←actor_id, ymov_id←movie_id, yr←role (roles)))
Z = X ⨯ Y
R = (π xid (σ xid = yid ∧ xmov_id ≠ ymov_id  (Z)))
π first_name, last_name (actors - ((ρ id←xid ( R ))⨝actors))
```

c. Wypisz imiona i nazwiska aktorów, którzy wyreżyserowali film, w którym grali.
Przemysław Stasiuk
```
A = π first_name, last_name, movie_id (movies ⨝ roles ⨝ (ρ actor_id←id actors))
B = A ⨝ movies_directors
C = B ⨝ directors
D = π first_name, last_name (C)
D
```

d. Wypisz tytuły filmów, które są gatunku Drama ale jednocześnie nie Sci-Fi.
Grzegorz Maliniak
```
A = π movie_id (σ genre = 'Drama' (movies_genres))
B = π movie_id (σ genre = 'Sci-Fi' (movies_genres))
C = A - B
D = π name ((ρ id ← movie_id (C)) ⨝ movies)
D
```

e. Wypisz pełne dane filmów z najniższą wartością atrybutu rank.
```
R = (ρ only_ranks (π rank movies))
M = (movies ⨯ R)
A = (π id, name, year, movies.rank (σ (movies.rank > only_ranks.rank) M))
Result = (movies - A)
Result

```

Natalia Piasta

f. Wypisz nazwiska aktorów, którzy zagrali taką samą rolę w conajmniej trzech różnych filmach.
Antoni Nowak

```
π actors.last_name 
  (actors ⨝ actors.id = roles.actor_id 
    (σ n >2 
      (γ roles.actor_id; count(roles.movie_id) -> n 
        (π actor_id,movie_id
          (σ actor_id = a1 ∧ movie_id ≠ m1 ∧ role = r1
            (roles ⨯ ρ a1←actor_id,m1←movie_id,r1←role roles))))))
```

Prościej:
```
π actors.last_name 
  (actors ⨝ actors.id = roles.actor_id 
    (σ n >2 
      (γ roles.actor_id, roles.role; count(roles.movie_id) -> n roles)))
```


g. Wypisz nazwiska reżyserów, którzy kręcą wyłacznie horrory.
Przemysław Hoszowski
```
π directors.last_name

(((π id directors) - 
-- odejmujemy tych co nakręcili film inny niż horror
(π movies_directors.director_id	 
	((σ genre != 'Horror' movies_genres) 
	⨝ movies_directors)))
⨝ directors)
```

h. Wypisz nazwiska rezyserów, którzy nakręcili film, w którym wśród aktorów jest dokładnie jedna kobieta.


Dominik Trautman
```
π last_name 
    (directors ⨝ id=director_id 
        (movies_directors ⨝ 
            (σ kobiety=1 
                (γ movie_id; count(gender)→kobiety 
                    π movie_id, actor_id, gender (σ gender = 'F' (roles ⨝ actor_id = id actors))))))
```



### Tabela deklaracji

:::info
:exclamation: W przypadku, gdy można zgłaszać chęć prezentacji zadania:

Gotowość rozwiązania zadania należy wyrazić poprzez postawienie X w odpowiedniej kolumnie! Jeśli pożądasz zreferować dane zadanie (co najwyżej jedno!) oznacz je znakiem ==X== (`==X==`).
:::


:::info
Gotowość rozwiązania zadania należy wyrazić poprzez postawienie X w odpowiedniej kolumnie!
:::

:::success
|                      |  1  |  2  |  3  |  4  |  5  | 6.1 | 6.2 | 6.3 | 6.4 |  7  |
| -------------------: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|     Wojciech Adamiec |     |     |     |     |     |     |     |     |     |     |
|     Karol Cieślawski |     |     |     |     |     |     |     |     |     |     |
| Przemysław Hoszowski |     |     |     |     |     |     |     |     |     |     |
|       Karol Kęciński |     |     |     |     |     |     |     |     |     |     |
|        Dominik Komła |     |     |     |     |     |     |     |     |     |     |
|    Grzegorz Maliniak |     |     |     |     |     |     |     |     |     |     |
|   Michał Mikołajczyk |     |     |     |     |     |     |     |     |     |     |
|    Barbara Moczulska |     |     |     |     |     |     |     |     |     |     |
|         Antoni Nowak |     |     |     |     |     |     |     |     |     |     |
|       Łukasz Orawiec |     |     |     |     |     |     |     |     |     |     |
|       Natalia Piasta |     |     |     |     |     |     |     |     |     |     |
|         Łukasz Pluta |     |     |     |     |     |     |     |     |     |     |
|     Dominik Trautman |     |     |     |     |     |     |     |     |     |     |
|        Jan Wańkowicz |     |     |     |     |     |     |     |     |     |     |
|         Nikola Wrona |     |     |     |     |     |     |     |     |     |     |
|           Adam Zyzik |     |     |     |     |     |     |     |     |     |     |
:::

## 16 marca

### Deklaracje

:::danger
|                      |  1  |  2  |  3  |  4  |  5  | 6.a | 6.b | 6.c | 6.d |  7  |
| -------------------: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|     Wojciech Adamiec |     |==X==|     |==X==|==X==|==X==|==X==|==X==|==X==|     |
|     Karol Cieślawski |==X==|==X==|     |==X==|==X==|==X==|==X==|==X==|==X==|     |
| Przemysław Hoszowski |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|     |
|       Karol Kęciński |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|
|        Dominik Komła |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|     |
|    Grzegorz Maliniak |     |==X==|     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|
|   Michał Mikołajczyk |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|     |
|    Barbara Moczulska |     |==X==|     |==X==|==X==|==X==|==X==|     |==X==|==X==|
|         Antoni Nowak |     |==X==|==X==|==X==|==X==|     |     |     |     |     |
|       Łukasz Orawiec |     |==X==|     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|
|       Natalia Piasta |     |     |     |     |     |     |     |     |     |     |
|         Łukasz Pluta |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|
|     Dominik Trautman |     |     |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|
|        Jan Wańkowicz |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|
|         Nikola Wrona |     |==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|==X==|
|           Adam Zyzik |     |     |     |     |     |     |     |     |     |     |
:::

### Rozwiązania

#### 1

Karol Cieślawski

:::info
![](https://i.imgur.com/2NZ8NVu.png)
:::

Wystarczy, że będziemy w stanie przedstawić każdy z operatorów $\pi, \sigma, \rho, \times, \cup$ za pomocą rrk albo rrd.

* (rrk) $\pi_{a_1, ..., a_n}R \equiv \{r'|\exists_{r} R(r) \wedge r.a_1=r'.a_1 \wedge \cdots \wedge r.a_n=r'.a_n \}$

* (rrk) $\rho_{b_1, ..., b_n}R \equiv \{r'|\exists_{r} R(r) \wedge r.a_1=r'.b_1 \wedge \cdots \wedge r.a_n=r'.b_n \}$

* (rrd) $A\times B\equiv \{(a_1,\cdots,a_n,b_1,\cdots,b_n)| A(a_1,\cdots,a_n) \wedge B(b_1,\cdots,b_n) \}$

* (rrk) $A \setminus B\equiv \{a|A(a) \wedge \neg B(a) \}$

* $\sigma_F(R)$
    * dla $F=k_1 \oplus k_2$
        $\sigma_F(R) \equiv \{r| R(r) \wedge r.k_1 \oplus r.k_2 \}$
        
    * dla $F = A \wedge B$ możemy zapisać wyrażenie jako $\sigma_A(\sigma_B(R))$, które jest równoważne

    * Negacja
        niech $A = \sigma_F(r)$, które umiemy otrzymać. Wtedy
        $\sigma_{\neg F}(R) \equiv \{r|R(r) \wedge \neg A(r)\}$
        
    * Alternatywa
        $F = A \lor B = \neg(\neg A \wedge \neg B)$
    * Alternatywa
        $A_i = \sigma_{F_i}(R)$
        $\sigma_{F_1\lor F_2}(R) = \{r| A_1(r) \lor A_2(r)\}$
        
Złożenia otrzymujemy składając powyższe definicje.

#### 2

:::info
Wojciech Adamiec
:::
:::info
![](https://i.imgur.com/xFbMoyH.png)
:::
Kiedy atrybut $Z$ relacji $S$ spełnia właśność klucza obcego wskazującego na atrybut $A$ relacji $R$?

Wtedy i tylko wtedy, gdy:
$\forall s\in S: (\neg isnull(s.z) \land \exists r \in R: r.a = s.z )$

Równoważnie, w notacji zbiorowej:
$\{s|s\in S \land \neg isnull(s.z) \land \exists r \in R: r.a = s.z  \} = S$

Aktualnie nasz zbiór jest pełen (zawiera wszystkie krotki z $S$), wtw gdy $Z$ spełnia warunek zadania. Musimy zanegować formułę w środku, aby zbiór był pusty gdy warunki zadania są spełnione:

$\{s|s\in S \land \neg(\neg isnull(s.z) \land \exists r \in R: r.a = s.z ) \} = \emptyset$

Można uprościć jeszcze formułę logiczną:

$\{s|s\in S \land (isnull(s.z) \lor \neg(\exists r \in R: r.a = s.z )) \} = \emptyset$

#### 3

Karol Kęciński

:::info
![](https://i.imgur.com/4pN3G29.png)
:::

![](https://i.imgur.com/5A2QqaT.png)

:::info
Pierwsze zapytanie zwraca największe *a* w relacji *R* spośród wszystkich *a*.
Ta formuła nie jest zależna od dziedziny, ponieważ rozpatrujemy tylko takie elementy które należą do relacji *R*.
:::

:::success
Formuła w algebrze relacji:

$\pi_{a}(R \setminus \pi_{a,b}(\sigma_{a<a'}(R \times \rho_{R(a',b')}R)))$
:::

![](https://i.imgur.com/dm2fFoz.png)

:::info
Zwraca takie pary *(a, b)*, że dla każdego *c* znajdującego się w relacji *T*, *c* jest w relacji w parze z *a* lub *b* lub relacja *T* jest pusta. 
Ta formuła jest zależna od dziedziny: gdyby T była pusta, otrzymalibyśmy wszystkie możliwe pary *(a, b)*.
:::

:::danger
Formuły w algebrze relacji zwracają zbiór skończony, więc nie da się zapisać równoważnej formuły w algebrze relacji.
:::


#### 4

Barbara Moczulska
![](https://i.imgur.com/xamxgaS.png)

![](https://i.imgur.com/SEasQHZ.png)
odpowiedź 1 i 3

#### 5

Łukasz Orawiec

Osoby chodzące tylko do jednego baru.

1. $\{o \ \vert \ (\exists b)(B(o,b) \ \land \ \lnot (\exists b')(B(o,b')))\}$

    :::danger
    Zwróci osoby, które bywają w pewnym barze i nie bywają w żadnym barze, czyli zbiór pusty.
    :::
    
<br/>

2. $\{o \ \vert \ (\exists b)(B(o,b))\}$
    
    :::danger
    Zwróci osoby bywające w przynajmniej jednym barze.
    :::
    
<br/>

3. $\{o \ \vert \ (\exists b)(B(o,b) \ \land \ \lnot (\exists b')(b \neq b' \ \land \ B(o,b')))\}$

    :::success
    Zwróci oczekiwany wynik.
    :::
    
    
<br/>

4. $\{o \ \vert \ (\exists b)(B(o,b) \ \land \ \lnot (\exists b', o')(b \neq b' \ \land \ o = o' \ \land \ B(o',b')))\}$


    :::success
    Zwróci oczekiwany wynik.
    :::

#### 6a

Dominik Trautman
![](https://i.imgur.com/7zaKdRK.png)



$\{ a\in A | (\forall_{ f, f'\in F} \exists_{r, r' \in R})(\\f.idf = r.idf \land f'.idf = r'.idf \land r.pseudo=a.pseudo \land r'.pseudo=a.pseudo$ $\Rightarrow f.rokProd = f'.rokProd) \}$

#### 6b

Przemysław Hoszowski

$\{f | f \in F \land \neg(\exists f_2)( f_2 \in F \land f_2.rezyser = f.rezyser \land f_2.rokProd > f.rokProd) \}$

#### 6c

Nikola Wrona

![](https://i.imgur.com/jQXMeCh.png)

$\{{Z^{[pseudo, idf, gaza]}| (\exists r)(r \in R \wedge r.pseudo=Z.pseudo \wedge r.idf = Z.idf \wedge r.gaza = Z.gaza)}$
${\wedge \neg(\exists r')(r' \in R \wedge r.pseudo \neq r'.pseudo \wedge r.idf = r'.idf \wedge r.gaza<r'.gaza)}\}$


#### 6d

Michał Mikołajczyk

:::info
Podaj pełne krotki aktorów, którzy nigdy nie obniżyli swojej minimalnej gaży (w późniejszych latach mogła ona najwyżej rosnąć). Na wynik nie wpływają lata, w których aktor nie podał minimalnej gaży. 
:::

$\{a \mid A(a) \wedge (\forall m_1,m_2 \in M) (\\
\;\;\;\;\;\;\; (m_1.pseudo = m_2.pseudo=a.pseudo \wedge m_1.rok < m_2.rok ) \implies\\
\;\;\;\;\;\;\;\;\;\;\;\;\;\; m_1.minGaza \leq m_2.minGaza\\
) \}$

#### 7

Łukasz Pluta

Weźmy relację D, która ma jedną kolumnę o nazwie 'x' i wartości {1, 2, NULL} w niej.

Niech $Q = \sigma_{x > 1}$

Teraz $Q(rep(D))$ zwróci wszystkie krotki z zakresu integer(zakładając że x było typu integer) oprócz 0 i 1, natomiast $rep(Qd)$ jest niezdefiniowane bo nie wiemy co zwraca selekcja dla NULLa.