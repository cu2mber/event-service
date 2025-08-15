import re
import json
import os
from datetime import datetime
from ..repository.crawler.db_connection import DBManager

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager


class EventCrawler:
    def __init__(self, debug=False, startPage=1):
        self.db = DBManager()
