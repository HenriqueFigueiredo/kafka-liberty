echo -n -e "\033]0;Producer - Kelvin\007"
mvn clean install liberty:run -Ddefault.http.port=9080