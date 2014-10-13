#!/usr/bin/python

import sys
import re
import time 

def convert_epoch_to_datetime(epoch_time):
    epoch_time = float(epoch_time)
    return time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(epoch_time))

def parse_data():
    store_count, store_percentile_95, store_epoch = "", "", ""

    while True:
        input = sys.stdin.readline()
        count, percentile_95, epoch = re.search(r"allRequests.all.count (\d+)", input), \
          re.search(r"allRequests.all.percentiles95 (\d+)", input), re.search(r"allRequests.all.count \d+ (\d+)", input)
        if count:
          store_count = count 
        if percentile_95: 
          store_percentile_95 = percentile_95 
        if epoch:
          store_epoch = epoch

        if store_count and store_percentile_95 and store_epoch:
            break
    return store_count, store_percentile_95, store_epoch

def print_data():
    count, percentile_95, epoch = parse_data()
    datetime = convert_epoch_to_datetime(epoch.group(1))
    sys.stdout.write("datetime, RPS, 95% \n")
    sys.stdout.write(datetime + ", " + count.group(1) + ", " + percentile_95.group(1) + "\n\n")

while True:
    try:
        print_data()
    except KeyboardInterrupt:
         sys.exit()
