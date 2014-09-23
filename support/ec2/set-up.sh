#!/bin/bash

# This is a set-up script for running Gatling on a BBC CentOS build.

echo "installing wget"
sudo yum install -y wget

echo "download SBT 0.13.6 RPM"
wget "https://dl.bintray.com/sbt/rpm/sbt-0.13.6.rpm"

echo "Installing SBT RPM"
sudo yum install -y "sbt-0.13.6.rpm"

exit
