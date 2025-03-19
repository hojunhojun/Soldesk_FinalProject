from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")  # 몽고디비 IP 주소
db = con.xe

# "rcp_parts" 필드가 비어 있는 서브 레시피 삭제
# sub_delete_result = db.recipe_sub_db.delete_many({"rcp_parts": ""})

# "rcp_id"가 3465인 메인 레시피 삭제
main_delete_result = db.recipe_main_db.delete_many({"rcp_id": 3875})
main_delete_result = db.recipe_main_db.delete_many({"rcp_id": 3876})

# 삭제된 문서 개수 각각 출력
# print(f"Deleted {sub_delete_result.deleted_count} recipes with empty rcp_parts")
print(f"Deleted {main_delete_result.deleted_count} recipes with rcp_id")

# MongoDB 연결 종료
con.close()
