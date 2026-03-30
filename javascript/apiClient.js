// apiClient.js

class ApiClient {
    constructor(baseUrl, options = {}) {
        this.baseUrl = baseUrl;
        this.cache = new Map();
        this.timeout = options.timeout || 5000;
        this.retries = options.retries || 2;
    }

    async get(endpoint) {
        const url = `${this.baseUrl}${endpoint}`;

        if (this.cache.has(url)) {
            return {
                fromCache: true,
                data: this.cache.get(url)
            };
        }

        let attempt = 0;

        while (attempt <= this.retries) {
            try {
                const controller = new AbortController();
                const timeoutId = setTimeout(() => controller.abort(), this.timeout);

                const response = await fetch(url, { signal: controller.signal });

                clearTimeout(timeoutId);

                if (!response.ok) {
                    throw new Error(`HTTP Error: ${response.status}`);
                }

                const data = await response.json();
                this.cache.set(url, data);

                return {
                    fromCache: false,
                    data
                };

            } catch (error) {
                attempt++;

                if (attempt > this.retries) {
                    throw new Error(`Request failed after ${attempt} attempts: ${error.message}`);
                }
            }
        }
    }
}

module.exports = ApiClient;