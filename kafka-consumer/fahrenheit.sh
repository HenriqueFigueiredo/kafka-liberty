echo -n -e "\033]0;Consumer - Fahrenheit\007"

if [ ! -f target/kafka-consumer.jar ]; then
  mvn package liberty:create liberty:install-feature liberty:deploy liberty:package -Dinclude=runnable
fi

java -Dtemperature.name=Fahrenheit -Ddefault.http.port=9082 -Dkelvin.diff=-459.67 -jar target/kafka-consumer.jar