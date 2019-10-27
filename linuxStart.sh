#!/bin/bash
mvn clean install
java -cp calculator-main/target/calculator-1.0-SNAPSHOT.jar pl.calculator.App
