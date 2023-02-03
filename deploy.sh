#!/bin/sh

mvn clean package shade:shade
yes yes| terraform apply
