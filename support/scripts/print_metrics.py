#!/usr/bin/python

import sys
import re

def parse_data():
    store_count, store_percentile_95 = "", ""

    while True:
        input = sys.stdin.readline()
        count, percentile_95 = re.search(r"allRequests.all.count (\d+)", input), \
          re.search(r"allRequests.all.percentiles95 (\d+)", input)
        if count:
          store_count = count 
        if percentile_95: 
          store_percentile_95 = percentile_95 

        if store_count and store_percentile_95:
            break
    return store_count, store_percentile_95

def print_data():
    count, percentile_95 = parse_data()
    sys.stdout.write("RPS, 95% \n")
    sys.stdout.write(count.group(1) + ", " + percentile_95.group(1) + "\n\n")

while True:
    try:
        print_data()
    except KeyboardInterrupt:
         sys.exit()


