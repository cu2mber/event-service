from dotenv import load_dotenv
import os
import jaydebeapi

load_dotenv(".env")

db_url = os.getenv("DB_URL")
db_user = os.getenv("DB_USER")
db_password = os.getenv("DB_PASSWORD")
db_driver = os.getenv("DB_DRIVER")
db_jar = os.getenv("DB_JAR")
class DBManager:
    def __init__(self):
        conn = jaydebeapi.connect(
            db_driver,
            db_url,
            [db_user, db_password],
            db_jar
        )

        cursor = conn.cursor()
        # cursor.execute("SELECT * FROM events")
        result = cursor.fetchall()

