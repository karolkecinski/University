-- Karol Kęciński, grupa mpy

-- Zadanie 1
SELECT city.* FROM city
JOIN airport ON (city.name = airport.city)
WHERE city.country='PL'
AND city.elevation < 100 
ORDER BY city.name;


-- Zadanie 2
SELECT DISTINCT Sea.name, Sea.area
FROM Sea
JOIN River ON (Sea.name = River.sea)
JOIN geo_river ON (River.name = geo_river.river)
WHERE geo_river.country = 'F'
AND River.length > 800
ORDER BY Sea.area DESC;
