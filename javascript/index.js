// index.js

const ApiClient = require('./apiClient');

(async () => {
    const client = new ApiClient("https://jsonplaceholder.typicode.com", {
        timeout: 4000,
        retries: 3
    });

    try {
        console.log("Pierwsze pobranie...");
        const result1 = await client.get("/todos/1");
        console.log(result1);

        console.log("\nDrugie pobranie (powinno użyć cache)...");
        const result2 = await client.get("/todos/1");
        console.log(result2);

    } catch (err) {
        console.error("Błąd:", err.message);
    }
})();