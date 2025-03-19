from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")  # 몽고디비 IP 주소
db = con.xe

# 기존 메인 레시피 데이터에 rcp_favorite 필드 추가 (기본값 0으로 설정)
db.recipe_main_db.update_many(
    {},  # 모든 레시피 데이터에 대해 적용
    {"$set": {"rcp_favorite": 0}}  # rcp_favorite 필드 추가 (기본값 0)
)

# 기존 서브 레시피 데이터에 rcp_favorite 필드 추가 (기본값 0으로 설정)
db.recipe_sub_db.update_many(
    {},  # 모든 레시피 데이터에 대해 적용
    {"$set": {"rcp_favorite": 0}}  # rcp_favorite 필드 추가 (기본값 0)
)

con.close()  # MongoDB 연결 종료