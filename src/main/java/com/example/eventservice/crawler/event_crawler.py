import requests as req
import json
from datetime import datetime
from ..repository.crawler.db_connection import DBManager
from dotenv import load_dotenv
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





