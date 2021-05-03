from math import sqrt
def nowy_kwadrat(x, y, a):
    if a>0:
        return (0, x, y, a)
    else:
        print("Błędna długość boku")

def nowe_kolo(x, y, r):
    if r>0:
        return [1, x, y, r]
    else:
        print("Błędna długość promienia")

def nowy_trojkat(x, y, b):
    if b>0:
        return [2, x, y, b]
    else:
        print("Błędna długość boku")

def pole(fig):

    typefig = fig[0]
    x = fig[1]
    y = fig[2]
    d = fig[3]

    if typefig==0:
        return d * d
    elif typefig==1:
        return 3.14 * d * d
    else:
        return (d * d * sqrt(3) / 4)

"""def przesun(fi, dx, dy):
     
    typefig = fig[0]
    x = fig[1]
    y = fig[2]
    d = fig[3]

    if typefig==0:
        return nowy_kwadrat(x+dx, y+dy, d)
    elif typefig==1:
        return nowe_kolo(x+dx, y+dy, d)
    else:
        return nowy_trojkat(x+dx, y+dy, d)"""

def przesun(fi, dx, dy):

    fi[1] = fi[1] + dx
    fi[2] = fi[2] + dy

def sumapol(tab, n):

    suma = 0

    for element in tab:
        suma += pole(element)

    return suma

figura=nowy_trojkat(0,0,4)
print(pole(figura))
print(figura)
przesun(figura, 2, 4)
print(figura)

F=[]
F.append(nowy_trojkat(0,1,2))
F.append(nowy_kwadrat(0,0,3))
F.append(nowe_kolo(3,3,1))

print(sumapol(F,3))