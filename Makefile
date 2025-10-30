.DEFAULT_GOAL := build-run

setup:
	./gradlew wrapper --gradle-version 8.13

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

run-dist:
	./app/build/install/app/bin/app

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

update-deps:
	./gradlew refreshVersions

build-run: build run

.PHONY: build