import pandas as pd
from pandas import read_csv
import math
import numpy as np
from pandas import read_excel
from datetime import datetime

import cv2
import os
import xlsxwriter


class Estimator:
    # session_time = 1799
    counter = 0
    data = None
    cumulative_data = dict()
    time_data = None

    set_1_min = 0
    set_2_min = 0
    set_3_min = 0
    set_4_min = 0
    set_1_mean = 0
    set_2_mean = 0
    set_3_mean = 0
    set_4_mean = 0
    set_1_max = 0
    set_2_max = 0
    set_3_max = 0
    set_4_max = 0

    # task_1,rest_1,task_2,rest_2,task_3, rest_3,task_4

    def to_string(self):
        print(self.set_1_min, "  " ,  self.set_2_min, "  " , self.set_3_min, "  " , self.set_4_min)
        print(self.set_1_mean, "  " ,  self.set_2_mean, "  " , self.set_3_mean, "  " , self.set_4_mean)
        print(self.set_1_max, "  " ,  self.set_2_max, "  " , self.set_3_max, "  " , self.set_4_max)

    def getFile(self, file):
        self.data = read_csv(file)

    def get_counter(self, session_time):
        self.counter = len(self.data) / session_time;
        start_rec = int(counter*180)

        # ignore first three minutes
        # self.data = self.data.iloc[start_rec:,:]

    def get_time_data_file(self,file):
        self.time_data = read_excel(file)

    def getStats(self,i):
        ses_start = datetime.strptime(str(self.time_data.iloc[i,0]), '%H:%M:%S')

        t1_start  = datetime.strptime(str(self.time_data.iloc[i,1]), '%H:%M:%S')
        t1_end  = datetime.strptime(str(self.time_data.iloc[i,2]), '%H:%M:%S')

        t2_start  = datetime.strptime(str(self.time_data.iloc[i,3]), '%H:%M:%S')
        t2_end  = datetime.strptime(str(self.time_data.iloc[i,4]), '%H:%M:%S')

        t3_start  = datetime.strptime(str(self.time_data.iloc[i,5]), '%H:%M:%S')
        t3_end  = datetime.strptime(str(self.time_data.iloc[i,6]), '%H:%M:%S')

        t4_start  = datetime.strptime(str(self.time_data.iloc[i,7]), '%H:%M:%S')
        t4_end  = datetime.strptime(str(self.time_data.iloc[i,8]), '%H:%M:%S')

        counter = (len(self.data) - 2)/(t4_end - ses_start).seconds;

        task_1_data = self.data.iloc[(int(counter* (t1_start - ses_start).seconds)) : (int(counter* (t1_end - ses_start).seconds)),:]
        task_2_data = self.data.iloc[(int(counter* (t2_start - ses_start).seconds)) : (int(counter* (t2_end - ses_start).seconds)),:]
        task_3_data = self.data.iloc[(int(counter* (t3_start - ses_start).seconds)) : (int(counter* (t3_end - ses_start).seconds)),:]
        task_4_data = self.data.iloc[(int(counter* (t4_start - ses_start).seconds)) : (int(counter* (t4_end - ses_start).seconds)),:]

        print("task 1 start is ", int(counter* (t1_start - ses_start).seconds))
        print("task 1 end is ", int(counter* (t1_end - ses_start).seconds))

        print("task 2 start is ", int(counter* (t2_start - ses_start).seconds))
        print("task 2 end is ", int(counter* (t2_end - ses_start).seconds))

        print("task 3 start is ", int(counter* (t3_start - ses_start).seconds))
        print("task 3 end is ", int(counter* (t3_end - ses_start).seconds))

        print("task 4 start is ", int(counter* (t4_start - ses_start).seconds))
        print("task 4 end is ", int(counter* (t4_end - ses_start).seconds))

        return task_1_data,task_2_data,task_3_data,task_4_data

    def estimate(self,ix):
        splits = self.getStats(ix)
        i = 0
        temp_data_list = list()

        for split_data in splits:
            temp_data_map = dict()
            i = i + 1
            mean_dividend = 0
            min = 0
            mean = 0
            sum = 0
            max = 0
            for value in split_data.values:
                mean_dividend = mean_dividend + 1

                value = float(value)
                # value = int(value)

                # print("Value is ", value)
                if mean_dividend == 1:
                    min = value
                if value < min:
                    min = value
                elif value > max:
                    max = value
                sum = (sum + value)

            mean = sum / mean_dividend
            # print("Min", min)
            # print("Mean", mean)
            # print("Max", max)

            if i == 1:
                # self.set_1_max = max
                temp_data_map['set_1_max'] = max
                # self.set_1_mean = mean
                temp_data_map['set_1_mean'] = mean
                # self.set_1_min = min
                temp_data_map['set_1_min'] = min
            elif i == 2:
                # self.set_2_max = max
                # self.set_2_mean = mean
                # self.set_2_min = min
                temp_data_map['set_2_max'] = max
                temp_data_map['set_2_mean'] = mean
                temp_data_map['set_2_min'] = min
            elif i == 3:
                # self.set_3_max = max
                # self.set_3_mean = mean
                # self.set_3_min = min
                temp_data_map['set_3_max'] = max
                temp_data_map['set_3_mean'] = mean
                temp_data_map['set_3_min'] = min
            elif i == 4:
                # self.set_4_max = max
                # self.set_4_mean = mean
                # self.set_4_min = min
                temp_data_map['set_4_max'] = max
                temp_data_map['set_4_mean'] = mean
                temp_data_map['set_4_min'] = min
            temp_data_list.append(temp_data_map)
        return temp_data_list

    def estimateFile(self, file, cand_count, time_file):
        print("Checking file ", file)
        self.getFile(file)
        self.get_time_data_file(time_file)
        # obj.reduce_data(session_time)
        # self.getStats(cand_count)
        return self.estimate(cand_count)

    def iterateOverDirectory(self, data_dir, tim_file):
        i = 0
        cumulative_data = dict()
        dir_list = [name for name in os.listdir(data_dir)
                    if os.path.isdir(os.path.join(data_dir, name))]
        # print(len(dir_list))

        for name in dir_list:
            session_data = dict()
            hr_file = data_dir + "/" + name + "/HR.csv"

            hr_stats = self.estimateFile(hr_file,cand_count=i, time_file=tim_file)

            eda_file = data_dir + "/"  + name + "/EDA.csv"
            eda_stats = self.estimateFile(eda_file,cand_count=i, time_file=tim_file)

            temp_file = data_dir + "/"  + name + "/TEMP.csv"
            temp_stats = self.estimateFile(temp_file,cand_count=i, time_file=tim_file)

            session_data['HR'] = hr_stats
            session_data['EDA'] = eda_stats
            session_data['TEMP'] = temp_stats

            cumulative_data[str(i)] = session_data
            i = i + 1
            # print(i)

        return cumulative_data



obj = Estimator()

final_data = obj.iterateOverDirectory(data_dir="D:/Semester 2/SOFTENG 702/Results", tim_file="D:/Semester 2/SOFTENG 702/Results/sessionData.xlsx")

# write to excel
workbook = xlsxwriter.Workbook('data.xlsx')
worksheet = workbook.add_worksheet()

worksheet.write(0, 0, "Candidate")
worksheet.write(0, 1, "Vitals")
worksheet.write(0, 2, "Task")
worksheet.write(0, 3, "Max")
worksheet.write(0, 4, "Mean")
worksheet.write(0, 5, "Min")

row = 1

for k1, v1 in final_data.items():
    print(k1)
    worksheet.write(row, 0, k1)
    for k2, v2 in v1.items():
        print("    ", k2)
        worksheet.write(row, 1, k2)
        task = 1
        for v3 in v2:
            # print("        ", v3)
            col = 3
            for k4, v4 in v3.items():
                worksheet.write(row, 2, task)
                worksheet.write(row, col, v4)
                # print("        ", k4, "        ", v4)
                col = col + 1
            task = task + 1
            row = row + 1

workbook.close()