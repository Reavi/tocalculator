#!/bin/bash
if [ ! -d plugin ]; then
  mkdir -p plugin
fi

if [ -e calculator-main/target/calculator-main-1.0-SNAPSHOT.jar ]
then
	java -cp calculator-main/target/calculator-main-1.0-SNAPSHOT.jar pl.calculator.App
else
	mvn clean install
	java -cp calculator-main/target/calculator-main-1.0-SNAPSHOT.jar pl.calculator.App
fi
