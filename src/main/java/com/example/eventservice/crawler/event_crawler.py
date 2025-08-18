import requests as req
import json
from datetime import datetime

from ..repository.crawler.db_connection import DBManager
from dotenv import load_dotenv
import re
import os
import time

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options

load_dotenv(".env")

event_url = os.getenv("EVENT_URL")

class EventCrawler:
    def __init__(self, debug=False, startPage=1):
        # 데이터베이스 불러오는 함수
        self.db = DBManager()

        driver = webdriver.Chrome()
        driver.get(event_url)

        # 페이지가 완전히 로딩되도록 3초동안 기다림
        time.sleep(3)

        # 목록에 있는 축제 클릭
        start_event = driver.find_element(By.CLASS_NAME, ".go")
        start_event.click()

        # 상세 페이지에서 데이터 크롤링
        self.scrape_event()

    def scrape_event(self):
        d = self.driver

        # 타이틀 크롤링
        title = d.find_element(By.CSS_SELECTOR, "div.title > h3").text.strip()

        # 상세정보 (dl > dt/dd 구조)
        details = {}
        rows = d.find_elements(By.CSS_SELECTOR, "div.view_info dl")

        for row in rows:
            try:
                key = row.find_element(By.TAG_NAME, "dt").text.strip()
                value = row.find_element(By.TAG_NAME, "dd").text.strip()
                details[key] = value
            except Exception:
                continue

        print("축제명:", title)
        print("상세정보:", details)

        # 축제 DB에 저장
        sql = """
        INSERT INTO events (
            local_id, category_no, event_name, event_adderess,
            event_start_date, event_end_date,
            event_start_time, event_end_time,
            event_url, event_price, event_type,
            age_restriction, evevt_inquiry, event_description
        ) VALUES (
            %s, %s, %s, %s,
            %s, %s,
            %s, %s,
            %s, %s, %s,
            %s, %s, %s
        )
        """

        #TODO : events 테이블 논의 후 수정
        self.db.execute(
            sql,
            [
                0,  # local_id (임의 값)
                0,  # category_no (임의 값)
                title,  # event_name
                details.get("개최지역"),
                details.get("개최기간"),
                details.get("축제성격"),
                details.get("관련 누리집"),
                details.get("축제장소"),
                details.get("요금"),
                details.get("주최/주관기관"),
                details.get("문의"),
                None   #TODO event_description (본문 따로 크롤링 넣어야 함)
            ],
        )
        self.db.commit()
        print("✅ DB 저장 완료")

    """
    개최기간 문자열을 파싱해서 (start_date, end_date, start_time, end_time) 반환
    """
    def parse_period(period_text : str):
        try:
            # 예시: "2025. 08.14 ~ 08.16 : 10:00~21:00"
            # 날짜 부분 추출
            date_match = re.search(r"(\d{4}\.\s*\d{2}\.\d{2})\s*~\s*(\d{2}\.\d{2})", period_text)
            if not date_match:
                return None, None, None, None

            start_date_str = date_match.group(1).replace(" ", "")
            end_date_str = date_match.group(2).replace(" ", "")

            # 연도 추출해서 end_date 완성
            year = start_date_str[:4]
            start_date = datetime.strptime(start_date_str, "%Y.%m.%d").date()
            end_date = datetime.strptime(year + "." + end_date_str, "%Y.%m.%d").date()

            # 시간 부분 추출
            time_match = re.search(r"(\d{2}:\d{2})~(\d{2}:\d{2})", period_text)
            if time_match:
                start_time = datetime.strptime(time_match.group(1), "%H:%M").time()
                end_time = datetime.strptime(time_match.group(2), "%H:%M").time()
            else:
                start_time, end_time = None, None

            return start_date, end_date, start_time, end_time
        except Exception as e:
            print("⚠️ 기간 파싱 실패:", e)
            return None, None, None, None

if __name__ == "__main__":
    EventCrawler()









