#!/bin/bash

for dir in *; do
  if [ -f $dir/_DS_Store ]; then
    mv $dir/_DS_Store $dir/.DS_Store
  fi
done

