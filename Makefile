# Список сервисов (папок)
SERVICES = accounts cards loans configserver eurekaserver gatewayserver

# Цель по умолчанию — собрать всё
all: $(SERVICES)

# Правило для сборки каждого сервиса
$(SERVICES):
	mvn -f $@ compile jib:dockerBuild

push:
	docker image push docker.io/blacklacost/accounts:s9
	docker image push docker.io/blacklacost/cards:s9
	docker image push docker.io/blacklacost/loans:s9
	docker image push docker.io/blacklacost/configserver:s9
	docker image push docker.io/blacklacost/eurekaserver:s9
	docker image push docker.io/blacklacost/gatewayserver:s9

.PHONY: all $(SERVICES) push