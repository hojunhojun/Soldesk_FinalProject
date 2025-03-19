from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")
db = con.xe

# 레시피 서브 데이터를 조회하여 업데이트
for n in db.recipe_sub_db.find():
    updated_fields = {}
    count = 1  # 번호를 1부터 시작
    
    for i in range(1, 21):  # 1부터 20까지 순차적으로 확인
        key = f"rcp_mul{i:02d}"
        value = n.get(key, "")
        
        if value:  # 값이 비어 있지 않으면
            updated_fields[key] = f"{count}. {value}"  # 앞에 번호를 붙여서 저장
            count += 1  # 번호를 증가시킴

    # 필드를 업데이트
    if updated_fields:  # 만약 업데이트할 필드가 있다면
        db.recipe_sub_db.update_one({"_id": n["_id"]}, {"$set": updated_fields})

# MongoDB 연결 종료
con.close()