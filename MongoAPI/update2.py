import re
from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")  # 몽고디비 IP 주소
db = con.xe

# 숫자, 알파벳(a, b, c 등), 괄호 안 숫자, 단위는 남기고 나머지 제거하는 함수
def clean_recipe_step(value):
    # (2)와 같은 괄호 안 숫자 제거
    value = re.sub(r'\(\d+\)', '', value)
    
    # 알파벳(a, b, c 등) 삭제 (소문자만 대상)
    value = re.sub(r'[a-zA-Z]', '', value)
    
    # 앞에 있는 숫자. 제거 (1., 2. 같은)
    value = re.sub(r'^\d+\.', '', value)
    
    # cm, g, ml 같은 단위(2글자 이상)는 남긴다
    value = re.sub(r'(?<=\d)(cm|g|ml|kg|oz|tbsp|tsp|L|mL|fl|oz|cup|pt|qt|tbsp|tsp|lb|lbs)', '', value)
    
    # 공백을 제거하거나 필요에 따라 정리
    return value.strip()

# 업데이트할 동적 필드만 수정
for n in db.recipe_sub_db.find():
    updated_fields = {}

    # 1부터 20까지 동적 필드를 확인
    for i in range(1, 21):  # 1부터 20까지 순차적으로 확인
        key = f"rcp_mul{i:02d}"  # 동적 필드명 (rcp_mul01, rcp_mul02, ...)
        value = n.get(key, "")  # 해당 필드의 값

        if value:  # 값이 비어 있지 않으면
            # value에서 숫자, 알파벳, 괄호 안 숫자 등을 제거한 후 업데이트
            clean_value = clean_recipe_step(value)
            updated_fields[key] = clean_value  # 필드에 수정된 값 넣기

    # 동적 필드만 업데이트
    if updated_fields:  # 업데이트할 동적 필드가 있다면
        db.recipe_sub_db.update_one(
            {"_id": n["_id"]},  # _id로 레시피를 찾기
            {"$set": updated_fields}  # 동적 필드만 업데이트
        )

# MongoDB 연결 종료
con.close()
