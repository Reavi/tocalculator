#!/bin/bash
if [ -e calculator-main/target/calculator-main-1.0-SNAPSHOT.jar ]
then
	java -cp calculator-main/target/calculator-main-1.0-SNAPSHOT.jar pl.calculator.App
else
	mvn clean install
	java -cp calculator-main/target/calculator-main-1.0-SNAPSHOT.jar pl.calculator.App
fi
