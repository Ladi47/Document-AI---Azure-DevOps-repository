# api_client.py

import requests
import time

class ApiClient:
    def __init__(self, base_url, timeout=5, retries=2):
        self.base_url = base_url
        self.timeout = timeout
        self.retries = retries
        self.cache = {}

    def get(self, endpoint):
        url = f"{self.base_url}{endpoint}"

        if url in self.cache:
            return {
                "from_cache": True,
                "data": self.cache[url]
            }

        attempt = 0

        while attempt <= self.retries:
            try:
                response = requests.get(url, timeout=self.timeout)

                if response.status_code != 200:
                    raise Exception(f"HTTP Error: {response.status_code}")

                data = response.json()
                self.cache[url] = data

                return {
                    "from_cache": False,
                    "data": data
                }

            except Exception as e:
                attempt += 1
                if attempt > self.retries:
                    raise RuntimeError(
                        f"Request failed after {attempt} attempts: {str(e)}"
                    )
                time.sleep(0.5)  # krótki backoff
