import sys
import re

def parse_data(data):
  if data:
    match = re.search(r"allRequests.all.count (\d+)", input)
    if match:
      sys.stdout.write("RPS: " + match.group(1) + "\n")

while 1:
    try:
        input = sys.stdin.readline()
        parse_data(input)
    except KeyboardInterrupt:
         sys.exit()


