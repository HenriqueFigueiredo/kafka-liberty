echo -n -e "\033]0;Producer - Kelvin\007"

if [ ! -f target/kafka-producer.jar ]; then
  mvn package liberty:create liberty:install-feature liberty:deploy liberty:package -Dinclude=runnable
fi

java -Ddefault.http.port=9080 -jar target/kafka-producer.jar