from http.client import HTTPSConnection
from urllib.parse import quote
from json import loads
from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")  # 몽고디비 IP 주소
db = con.xe


db.recipe_rec_db.drop()

# API로부터 데이터를 받아오기 위한 연결
hc = HTTPSConnection("openapi.foodsafetykorea.go.kr")
hc.request("GET", "/api/#개발자키/COOKRCP01/json/1001/1137")
resBody = hc.getresponse().read()

reccount = 0 
rcp_id = 0

    # 레시피 추천수 테이블에 삽입
for b in loads(resBody)["COOKRCP01"]["row"]:
    db.recipe_rec_db.insert_one({
        "rcp_id": rcp_id,
        "rec_count": reccount  # 추천수
    })
# hc.close()  # API 연결 종료
con.close()  # MongoDB 연결 종료
