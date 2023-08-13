#!/bin/bash
LIBRARY_DIR=$1

while IFS="\n" read -r p 
do
  printf '%s\n' "$p"
done < preserve_list.txt
