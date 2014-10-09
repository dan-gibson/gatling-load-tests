#!/bin/bash

cd
# 0.9.x (stable) branch
git clone https://github.com/graphite-project/graphite-web.git
cd graphite-web
git checkout 0.9.x
cd ..
git clone https://github.com/graphite-project/carbon.git
cd carbon
git checkout 0.9.x
cd ..
git clone https://github.com/graphite-project/whisper.git
cd whisper
git checkout 0.9.x
sudo python setup.py install
cd ../carbon/
sudo python setup.py install
# configure carbon
cd /opt/graphite/conf
sudo cp carbon.conf.example carbon.conf
sudo cp storage-schemas.conf.example storage-schemas.conf

exit 0
