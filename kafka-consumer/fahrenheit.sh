echo -n -e "\033]0;Consumer - Fahrenheit\007"
mvn clean install liberty:run -Ddefault.http.port=9082 -Dtemperature.name="Fahrenheit" -Dkelvin.diff=-459.67