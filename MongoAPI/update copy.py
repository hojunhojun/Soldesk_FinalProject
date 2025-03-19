from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")  # 몽고디비 IP 주소
db = con.xe

# 메인 레시피에서 서브 레시피가 없는 rcp_id 찾기
missing_sub_ids = db.recipe_main_db.aggregate([
    {
        "$lookup": {
            "from": "recipe_sub_db",        # 서브 레시피 테이블
            "localField": "rcp_id",         # 메인 레시피의 rcp_id
            "foreignField": "rcp_id",       # 서브 레시피의 rcp_id
            "as": "sub_recipes"
        }
    },
    {
        "$match": {
            "sub_recipes": {"$size": 0}     # 서브 레시피가 없는 메인 레시피만 필터링
        }
    },
    {
        "$project": {
            "rcp_id": 1,
            "rcp_name": 1                   # 필요한 필드만 선택 (예: 레시피 이름)
        }
    }
])

# 결과 출력
for recipe in missing_sub_ids:
    print(f"Missing sub-recipe: {recipe['rcp_id']} - {recipe['rcp_name']}")


# 연결 종료
con.close()
