
# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy Java source and resources
COPY src ./src

# Compile TestNG tests
RUN mvn test-compile -B


# Stage 2: Runtime
FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Install Chrome dependencies
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    curl \
    ca-certificates \
    fonts-liberation \
    libglib2.0-0 \
    libnss3 \
    libfontconfig1 \
    && curl -fsSL https://dl.google.com/linux/linux_signing_key.pub \
    | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] https://dl.google.com/linux/chrome/deb/ stable main" \
    > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Copy complete project
COPY . .

ENV CHROME_BIN=/usr/bin/google-chrome-stable

# Execute Selenium TestNG tests
CMD ["mvn", "clean", "test"]
