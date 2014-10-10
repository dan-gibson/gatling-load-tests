import sys
while 1:
    try:
        input = sys.stdin.readline()
        if input:
            sys.stdout.write(input)
    except KeyboardInterrupt:
         sys.exit()
