import os, time
from dotenv import load_dotenv

from com.example.eventservice.pyservice.crawler.event_crawler import EventCrawler

load_dotenv(".env")
event_url = os.getenv("EVENT_URL")

# pytest로 통합 테스트. 20개 지역 축제 크롤링
def test_crawl_20_events():

    crawler = EventCrawler()
    d = crawler.driver

    crawled_count = 0

    while crawled_count < 2:
        try:
            crawler.scrape_event()
            crawled_count += 1
        except Exception as e:
            print(f"크롤링 중 오류 발생 : {e}")
            d.back()
            time.sleep(1)

    print(f"총 {crawled_count}개 축제 크롤링 완료")

    rows = crawler.db.fetchall("SELECT COUNT(*) FROM events")
    print("DB에 저장된 축제 개수:", rows[0][0])

    crawler.db.close()
    crawler.driver.quit()



