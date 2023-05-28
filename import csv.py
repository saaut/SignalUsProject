import csv
f = open('out.csv', 'w')
data = ['가나다라','12345']
writer = csv.writer(f)
writer.writerow(data)
f.close()