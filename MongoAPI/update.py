from pymongo import MongoClient

# MongoDB 클라이언트 연결
client = MongoClient('IP주소')
db = client.xe

# 메인 레시피의 모든 rcp_name 가져오기
main_recipe_names = db.recipe_main_db.distinct("rcp_name")

# 메인 레시피 기준으로 서브 레시피의 rcp_id 업데이트
main_recipes = db.recipe_main_db.find()

for main_recipe in main_recipes:
    rcp_name = main_recipe["rcp_name"]
    main_rcp_id = main_recipe["rcp_id"]

    # 서브 레시피 rcp_name이 메인 레시피와 일치하는 경우 rcp_id 업데이트
    db.recipe_sub_db.update_many(
        {"rcp_name": rcp_name},
        {"$set": {"rcp_id": main_rcp_id}}
    )

# 메인 레시피에 없는 rcp_name을 가진 서브 레시피 삭제
delete_result = db.recipe_sub_db.delete_many(
    {"rcp_name": {"$nin": main_recipe_names}}  # 메인 레시피에 없는 rcp_name만 삭제
)

print(f"{delete_result.deleted_count}개의 서브 레시피가 삭제되었습니다.")

# MongoDB 연결 종료
client.close()
