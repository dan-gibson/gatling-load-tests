#!/bin/bash

# This is a set-up script for running Gatling on a BBC CentOS build.

echo "Goto HOME"
cd

echo "download SBT 0.13.6 RPM"
wget "https://dl.bintray.com/sbt/rpm/sbt-0.13.6.rpm"

echo "Installing SBT RPM"
sudo yum install -y "sbt-0.13.6.rpm"

echo "Linux Tuning"
printf '*       soft    nofile  65535 \n*       hard    nofile  65535' | sudo tee /etc/security/limits.conf
sudo sh -c "printf 'UseLogin yes' >> /etc/ssh/sshd_config" 
# more ports for testing
sudo sysctl -w net.ipv4.ip_local_port_range="1025 65535"
# increase the maximum number of possible open file descriptors:
echo 300000 | sudo tee /proc/sys/fs/nr_open
echo 300000 | sudo tee /proc/sys/fs/file-max

echo "install Vim pathogen"
mkdir -p ~/.vim/autoload ~/.vim/bundle && \
  curl -LSso ~/.vim/autoload/pathogen.vim https://tpo.pe/pathogen.vim

echo "Vim scala plugin" 
cd ~/.vim/bundle
git clone https://github.com/derekwyatt/vim-scala

echo "Configure Vim" 
printf "execute pathogen#infect() syntax on
        filetype plugin indent on 
        set shortmess+=I" > ~/.vimrc 

echo "alias vi='vim'" > ~/.bashrc

# Might be an idea to now reboot
