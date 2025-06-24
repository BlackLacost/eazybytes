# Список сервисов (папок)
SERVICES = accounts cards loans configserver eurekaserver
PUSH_SERVICES = accounts cards loans configserver eurekaserver

# Цель по умолчанию — собрать всё
all: $(SERVICES)

# Правило для сборки каждого сервиса
$(SERVICES):
	mvn -f $@ compile jib:dockerBuild

push: $(PUSH_SERVICES)

$(PUSH_SERVICES):
	docker image push docker.io/blacklacost/$@:s8

.PHONY: all push $(SERVICES) $(PUSH_SERVICES)