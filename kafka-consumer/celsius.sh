echo -n -e "\033]0;Consumer - Celsius\007"
mvn clean install liberty:run -Ddefault.http.port=9081 -Dtemperature.name="Celsius" -Dkelvin.diff=-273.15