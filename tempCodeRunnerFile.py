        if(column_index==84 or column_index>84):
            with open('point.csv', 'a', newline='') as file:
                writer = csv.writer(file)
                writer.writerow('\''+dataForAdd+'\'')
                return