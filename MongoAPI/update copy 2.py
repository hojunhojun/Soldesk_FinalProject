from pymongo import MongoClient
import re

# MongoDB 연결
con = MongoClient("IP주소")
db = con.xe

# 레시피 서브 데이터를 조회하여 업데이트
for n in db.recipe_sub_db.find():
    rcp_parts = n.get('rcp_parts', '')
    cleaned_parts = re.sub(r'\b재료\b', '', rcp_parts).strip()

    # '재료' 제거 후 업데이트
    if rcp_parts != cleaned_parts:  # 변경 사항이 있을 때만 업데이트
        db.recipe_sub_db.update_one({"_id": n["_id"]}, {"$set": {"rcp_parts": cleaned_parts}})

# MongoDB 연결 종료
con.close()
