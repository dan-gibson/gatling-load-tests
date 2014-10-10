#!/usr/bin/python

import sys
import re

def parse_data(data):
  if data:
    count = re.search(r"allRequests.all.count (\d+)", input)
    minimum = re.search(r"allRequests.all.min (\d+)", input)
    maximum = re.search(r"allRequests.all.max (\d+)", input)
    if count: 
      sys.stdout.write("RPS: " + count.group(1) + "\n")
    if minimum:
      sys.stdout.write("Min: " + minimum.group(1) + "\n")
    if maximum:
      sys.stdout.write("Max: " + maximum.group(1) + "\n\n")

while 1:
    try:
        input = sys.stdin.readline()
        parse_data(input)
    except KeyboardInterrupt:
         sys.exit()


