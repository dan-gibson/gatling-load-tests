#!/bin/bash

echo "install Vim pathogen"
mkdir -p ~/.vim/autoload ~/.vim/bundle && \
  curl -LSso ~/.vim/autoload/pathogen.vim https://tpo.pe/pathogen.vim

echo "Vim plugins 
cd ~/.vim/bundle
git clone https://github.com/derekwyatt/vim-scala
git clone https://github.com/kien/ctrlp.vim

echo "Configure Vim" 
printf "execute pathogen#infect() \n
syntax on
filetype plugin indent on \n
set shortmess+=I \n
set wildignore+=*/target/* \n
colorscheme ir_black" > ~/.vimrc 

echo "Vim color"
mkdir ~/.vim/colors
cd ~/.vim/colors
wget "http://blog.toddwerth.com/entry_files/8/ir_black.vim"

echo "alias vi='vim'" > ~/.bashrc
