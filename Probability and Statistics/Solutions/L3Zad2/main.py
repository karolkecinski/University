sumy = [0, 0, 0, 0, 0, 0, 0]
dniMiesiecy = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
dzien_tygodnia = 0 # Poniedziałek, 1.01.1601 r.

for rok in range(1601,2001):

    for miesiac in range(1, 13):

        for dzien in range(1,dniMiesiecy[miesiac-1]+1):

            if dzien == 29 and miesiac == 2:
                if (rok % 4 != 0 or (rok % 100 == 0 and rok % 400!=0)):
                    continue

            if dzien == 13:
                sumy[ dzien_tygodnia] += 1
            dzien_tygodnia = (dzien_tygodnia + 1) % 7

print(sumy)


