import subprocess

cmd = ["/usr/bin/Rscript", "fuzzy-random-forest-R.r"]
subprocess.call(cmd)

x = subprocess.check_output(cmd, universal_newlines=True)

print('The maximum of the numbers is:', x)
