from http.client import HTTPSConnection
from urllib.parse import quote
from json import loads
from pymongo import MongoClient

# MongoDB 연결
con = MongoClient("IP주소")  # 몽고디비 IP 주소
db = con.xe

# db.recipe_sub_db.drop()
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

def clear(s, field_name=None):
    if s is None:  # None 체크
        return ""

    # rcp_parts만 <br>로 줄바꿈 처리
    if field_name == "RCP_PARTS_DTLS":
        s = s.replace("\n", "<br>")
    else:
        # 나머지 필드는 줄바꿈을 공백(" ")으로 처리
        s = s.replace("\n", " ")

    # "●" 기호 제거
    s = s.replace("●", "")

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
        rcp_name = clear(b["RCP_NM"], field_name="RCP_NM")  # 메뉴명
        print(f"Skipping recipe with ? in MANUAL fields: {rcp_name}")
        continue  # ?가 있으면 이 레시피는 건너뛰고 계속 진행
    
    # 세부 레시피 데이터
    rcp_id = get_next_sequence("rcp_id_seq")  # 새로운 rcp_id 생성
    rcp_name = clear(b["RCP_NM"], field_name="RCP_NM")  # 메뉴명
    rcp_category = clear(b["RCP_PAT2"], field_name="RCP_PAT2")  # 요리 종류
    rcp_mainBphoto = clear(b.get("ATT_FILE_NO_MK", ""), field_name="ATT_FILE_NO_MK")  # 요리사진(대)
    rcp_parts = clear(b.get("RCP_PARTS_DTLS", ""), field_name="RCP_PARTS_DTLS")  # 재료 정보
    
    # MANUAL 관련 필드들 (동적으로 처리)
    rcp_mul_fields = {}
    rcp_subimg_fields = {}
    
    for i in range(1, 21):  # 01부터 20까지
        manual_field = f"MANUAL{i:02d}"
        manual_img_field = f"MANUAL_IMG{i:02d}"
        # MANUAL01~MANUAL20 필드가 있으면 그 값을 추가
        if manual_field in b:
            rcp_mul_fields[f"rcp_mul{i:02d}"] = clear(b[manual_field], field_name=manual_field)
        if manual_img_field in b:
            rcp_subimg_fields[f"rcp_subimg{i:02d}"] = clear(b[manual_img_field], field_name=manual_img_field)
    
    # 영양 성분 처리
    rcp_if_eng = clear(b.get("INFO_ENG", ""), field_name="INFO_ENG")
    rcp_if_car = clear(b.get("INFO_CAR", ""), field_name="INFO_CAR")
    rcp_if_pro = clear(b.get("INFO_PRO", ""), field_name="INFO_PRO")
    rcp_if_fat = clear(b.get("INFO_FAT", ""), field_name="INFO_FAT")
    rcp_if_na = clear(b.get("INFO_NA", ""), field_name="INFO_NA")
    
    # 저감 조리 정보 처리
    rcp_tip = clear(b.get("RCP_NA_TIP", ""), field_name="RCP_NA_TIP")

    rcp_rec = 0  # 추천수 (기본값 0)
    rcp_chk = 0  # 조회수 (기본값 0)

    # 세부 레시피 테이블에 삽입
    db.recipe_sub_db.insert_one({
        "rcp_id": rcp_id,  # 메인 레시피 테이블과 연결
        "rcp_name": rcp_name,  # 메뉴명
        "rcp_category": rcp_category,  # 요리 종류
        "rcp_mainBphoto": rcp_mainBphoto,  # 요리 사진(대)
        "rcp_rec": rcp_rec,  # 추천수
        "rcp_parts": rcp_parts,  # 재료 정보
        **rcp_mul_fields,  # 동적으로 추가된 상세레시피 내용
        **rcp_subimg_fields,  # 동적으로 추가된 상세레시피 사진
        "rcp_if_eng": rcp_if_eng,  # 열량
        "rcp_if_car": rcp_if_car,  # 탄수화물
        "rcp_if_pro": rcp_if_pro,  # 단백질
        "rcp_if_fat": rcp_if_fat,  # 지방
        "rcp_if_na": rcp_if_na,  # 나트륨
        "rcp_tip": rcp_tip  # 저감 조리
    })

hc.close()  # API 연결 종료
con.close()  # MongoDB 연결 종료
