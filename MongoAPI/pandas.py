# -*- coding:utf-8 -*-
import pandas as pd
import numpy as np 
from http.client import HTTPSConnection
from urllib.parse import quote
from json import loads
from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")  # 몽고디비 IP 주소
db = con.xe
collection = db.recipe_sub_db

# rcp_name 필드가 존재하는 문서들의 개수 구하기
count = collection.count_documents({"rcp_name": {"$exists": True}})

print(f"'rcp_name' 필드의 값이 존재하는 문서의 개수: {count}")


# df = pd.read_txt("C:/Users/bjcum/Desktop/pasing/Tests333.txt", names=["rcp_rec"])
# print(df["rcp_rec"].count())


