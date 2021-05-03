def NWD(a, b):
    while b:
        a, b = b, a%b
    return a

def sprawdz(a, b):
    if b>0: return (a, b)
    elif a<0: return (abs(a), abs(b))
    else: return (a*(-1), abs(b))

def stworz_ulamek(a, b):
    n = NWD(abs(a),abs(b))
    a = a / n
    b = b / n

    return (a, b)

def dodaj(ul1, ul2):

    a = ul1[0]
    b = ul1[1]
    c = ul2[0]
    d = ul2[1]

    x = a * d + b * c 
    y = b * d
    
    n = NWD(abs(x),abs(y))
    
    x = x / n
    y = y / n
    print(y)
    return sprawdz(x, y)
    
def odejmij(ul1, ul2):
    
    a = ul1[0]
    b = ul1[1]
    c = ul2[0]
    d = ul2[1]

    x = a * d - b * c 
    y = b * d

    n = NWD(abs(x),abs(y))
    x = x / n
    y = y / n
    
    return sprawdz(x, y)

def pomnoz(ul1, ul2):

    a = ul1[0]
    b = ul1[1]
    c = ul2[0]
    d = ul2[1]

    x = a * c
    y = b * d

    n = NWD(abs(x),abs(y))
    x = x / n
    y = y / n

    return sprawdz(x, y)

def podziel(ul1, ul2):

    a = ul1[0]
    b = ul1[1]
    c = ul2[0]
    d = ul2[1]

    x = a * d
    y = b * c

    n = NWD(abs(x),abs(y))
    x = x / n
    y = y / n

    return sprawdz(x, y)

u1=stworz_ulamek(-2,-4)
u2=stworz_ulamek(1,-4)

print(dodaj(u1, u2))
print(odejmij(u1, u2))
print(pomnoz(u1, u2))
print(podziel(u1, u2))