import math as m
import matplotlib.pyplot as plt
import numpy as np

def G(x):
    return m.exp(-(x**2)/2)

def fi(t):
    return Romberg(t) / m.sqrt(2 * m.pi)

def Romberg(t):
    def romberg_method(m, n):
        if m != 0:
            new_el = ((((4**m) * Romberg_storage[m-1][n+1]) - Romberg_storage[m-1][n]) / ((4 ** m) -1))
            if n == 0:
                Romberg_storage.append([])
            pointer = Romberg_storage[m]
            pointer.append(new_el)
        elif n == 0:
            Romberg_storage[0][0] = ((G(0)+G(t)) * (t - 0)) / 2
        else:
            sum = 0
            for i in range(1, (2**(n-1))+1):
                sum += G(0 + (((2 * i) - 1) * h))
            Romberg_storage[0].append((Romberg_storage[0][n-1] / 2) + (sum * h))


    Romberg_storage = [[0 for i in range(1)] for j in range(1)]
    h = t / 2

    romberg_method(0,0)
    romberg_method(0,1)
    romberg_method(1,0)

    m = 1

    while abs(Romberg_storage[m][0] - Romberg_storage[m-1][0]) > 0.00000001:
        h = h / 2
        m = m + 1

        for i in range(m + 1):
            romberg_method(0+i, m-i)

    return Romberg_storage[m][0]

def answer(t):
    if t == 0:
        return 0.5
    elif t > 0:
        return (0.5 + fi(t))
    else:
        return (0.5 - fi(abs(t)))


x = np.linspace(-4,4,100)
y = []

for i in x:
    y.append(answer(i))

fig = plt.figure()
ax = fig.add_subplot(1, 1, 1)
#ax.spines['left'].set_position('center')
#ax.spines['bottom'].set_position('center')
ax.spines['right'].set_color('none')
ax.spines['top'].set_color('none')
ax.xaxis.set_ticks_position('bottom')
ax.yaxis.set_ticks_position('left')

plt.plot(x, y, 'g')
plt.show()

