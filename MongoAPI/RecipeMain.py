from http.client import HTTPSConnection
from urllib.parse import quote
from json import loads
from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("몽고디비 IP 주소")  # 몽고디비 IP 주소
db = con.xe

# db.recipe_main_db.drop()

# 시퀀스를 관리할 counter 컬렉션에 접근하여 rcp_id_seq 생성 및 증가
def get_next_sequence(name):
    # 시퀀스를 찾고, 없으면 새로 생성
    sequence_doc = db.counters.find_one({"_id": name})
    if sequence_doc is None:
        # 시퀀스를 새로 시작
        db.counters.insert_one({"_id": name, "seq": 1})
        return 1
    else:
        # 시퀀스를 증가시키고 업데이트
        new_seq = sequence_doc["seq"] + 1
        db.counters.update_one({"_id": name}, {"$set": {"seq": new_seq}})
        return new_seq

# API로부터 데이터를 받아오기 위한 연결
hc = HTTPSConnection("openapi.foodsafetykorea.go.kr")
hc.request("GET", "/api/#개발자키/COOKRCP01/json/1001/1137")
resBody = hc.getresponse().read()
# 응답 데이터에서 필요한 정보만 뽑아오기 위한 정리 함수
def clear(s):
    s = s.replace("●", "")
    s = s.replace("\n", "")
    return s
# API 응답에서 레시피 데이터를 읽어오기
for b in loads(resBody)["COOKRCP01"]["row"]:
    # MANUAL로 시작하는 모든 필드에 대해 ?가 있는지 확인
    skip_recipe = False
    for key, value in b.items():
        if key.startswith("MANUAL") and "?" in value:
            skip_recipe = True
            break  # 하나라도 ?가 있으면 건너뛰기
    # ?가 포함된 레시피는 건너뛰기
    if skip_recipe:
        rcp_name = clear(b["RCP_NM"])  # 메뉴명
        print(f"Skipping recipe with ? in MANUAL fields: {rcp_name}")
        continue  # ?가 있으면 이 레시피는 건너뛰고 계속 진행
    
    # 레시피 데이터를 가져옴
    rcp_name = clear(b["RCP_NM"])  # 메뉴명
    rcp_category = clear(b["RCP_PAT2"])  # 요리 종류
    rcp_mainphoto = clear(b["ATT_FILE_NO_MAIN"])  # 요리 사진
    rcp_rec = 0  # 추천수 (기본값 0)
    rcp_chk = 0  # 조회수 (기본값 0)
    # 새로운 rcp_id 생성
    rcp_id = get_next_sequence("rcp_id_seq")
    
    # 레시피 메인 테이블에 삽입
    db.recipe_main_db.insert_one({
        "rcp_id": rcp_id,  # 자동 생성된 고유 rcp_id
        "rcp_name": rcp_name,  # 메뉴명
        "rcp_category": rcp_category,  # 요리 종류
        "rcp_mainphoto": rcp_mainphoto,  # 요리 사진
        "rcp_rec": rcp_rec,  # 추천수
    })
hc.close()  # API 연결 종료
con.close()  # MongoDB 연결 종료
