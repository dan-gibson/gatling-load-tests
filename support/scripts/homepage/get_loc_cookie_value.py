import re

with open("loc-cookies.csv") as f:
  for line in f:
    match = re.search(r"i=(.+?):", line)
    print match.group(1)
