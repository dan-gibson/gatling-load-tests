#!/usr/bin/python

import sys
import re

def parse_data(data):
    count, minimum, maximum = re.search(r"allRequests.all.count (\d+)", data), \
        re.search(r"allRequests.all.min (\d+)", data), re.search(r"allRequests.all.max (\d+)", data)
    return count, minimum, maximum

def print_data(data):
    count, minimum, maximum = parse_data(data)
    if count: 
      sys.stdout.write("RPS: " + count.group(1) + "\n")
    if minimum:
      sys.stdout.write("Min: " + minimum.group(1) + "\n")
    if maximum:
      sys.stdout.write("Max: " + maximum.group(1) + "\n\n")

while True:
    try:
        input = sys.stdin.readline()
        if input:
            print_data(input)
    except KeyboardInterrupt:
         sys.exit()


