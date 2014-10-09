import re

sample_graphite_data = """gatling.sample.allRequests.ok.count 12 1412857940
                          gatling.sample.allRequests.ok.max 51 1412857940
                          gatling.sample.allRequests.ok.min 4 1412857940"""

def parse_data():
  rps = re.compile(r"ok.count (\d+)")
  rps.match(sample_graphite_data)
    
print parse_data()
 
