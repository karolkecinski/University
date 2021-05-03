-- Karol Kęciński, grupa mpy
-- Zadanie 1

SELECT Inside.country_name AS "Country name", Inside.island_count "Amount of islands" FROM(
	SELECT Country.name AS country_name, countq(DISTINCT islandin.island) AS island_count 
	FROM Country
	LEFT JOIN geo_sea ON (country.code = geo_sea.country)
	LEFT JOIN islandin ON (geo_sea.sea = islandin.sea)
	GROUP BY country.name
	ORDER BY island_count desc, country.name
) AS Inside;

-- Zadanie 2

SELECT country.name AS "Country name", ethnicgroup.percentage AS "Percentage of Poles"
FROM country
JOIN ethnicgroup
ON country.code = ethnicgroup.country
WHERE ethnicgroup.name = 'Polish' AND country.code IN 
    (SELECT country.code
     FROM ethnicgroup 
     JOIN country 
     ON country.code = ethnicgroup.country
     GROUP BY country.code
     HAVING COUNT(DISTINCT ethnicgroup.name) >= 10)
ORDER BY "Percentage of Poles" DESC;

-- Zadanie 3

SELECT * FROM Country WHERE Country.code IN (
	WITH RECURSIVE Path(c1, c2) AS (
		SELECT country1 AS c1, country2 AS c2 FROM Borders
		UNION ALL
		SELECT b.country1, p.c2 FROM Borders b JOIN Path p ON (b.country2 = p.c1)) 
		
	SELECT c1 AS a FROM Path
	WHERE Path.c2 = 'PL' OR Path.c1 = 'PL'
	GROUP BY a
	UNION ALL
	SELECT c2 AS b FROM Path
	GROUP BY b
)
ORDER BY Country.name;

-- Zadanie 4
SELECT foo.cn AS "Country name", FLOOR((foo.cip / foo.cp)*100) AS "Percentage"
FROM (SELECT Country.name AS cn, Country.population AS cp, City.population AS cip, City.name AS cin FROM Country
JOIN City ON (Country.code = City.country)
WHERE City.population > CEIL(Country.population * 0.75)) AS foo;




