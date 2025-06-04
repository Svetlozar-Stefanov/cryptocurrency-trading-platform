# Project Setup & Usage

This guide will walk you through the steps to set up and run the project locally.

---

## Usage

Follow these steps to get started:

### 1. Clone the Repository

```bash
git clone git@github.com:Svetlozar-Stefanov/cryptocurrency-trading-platform.git
cd ./cryptocurrency-trading-platform
```

### 2. Build Java Backend Project
```bash
cd ./backend
./mvnw package
```

### 3. Build and Run Docker Compose
```bash
cd {project-root}
docker compose build
docker compose run
```