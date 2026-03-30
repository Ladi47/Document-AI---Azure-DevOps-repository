# main.py

from api_client import ApiClient

def main():
    client = ApiClient(
        base_url="https://jsonplaceholder.typicode.com",
        timeout=4,
        retries=3
    )

    print("Pierwsze pobranie...")
    result_1 = client.get("/todos/1")
    print(result_1)

    print("\nDrugie pobranie (powinno użyć cache)...")
    result_2 = client.get("/todos/1")
    print(result_2)


if __name__ == "__main__":
    main()