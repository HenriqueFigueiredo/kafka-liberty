echo -n -e "\033]0;Consumer - Celsius\007"

if [ ! -f target/kafka-consumer.jar ]; then
  mvn package liberty:create liberty:install-feature liberty:deploy liberty:package -Dinclude=runnable
fi

java -Dtemperature.name=Celsius -Ddefault.http.port=9081 -Dkelvin.diff=-273.15 -jar target/kafka-consumer.jar