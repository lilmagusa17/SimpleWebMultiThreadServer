# Simple Multi-Threaded Web Server

## Overview
This project is a simple multi-threaded web server implemented in Java for Internet and Computing II. 
It listens for incoming HTTP requests on port **6789** and serves static files (HTML, images, etc.) stored in the `src/main/resources` directory.

## Features
- Handles multiple client requests simultaneously using threads.
- Supports **GET** requests for static files (HTML, JPEG, GIF, etc.).
- Returns `404 Not Found` if a requested file is missing.
- Serves files based on MIME type detection.

## How It Works
1. The server starts and listens for incoming connections on port **6789**.
2. When a client requests a file (e.g., `localhost:6789/index.html`), the server:
   - Checks if the file exists.
   - Reads and sends the file's content to the client.
   - Sends an appropriate HTTP response header.
3. If the requested file is missing, the server returns a `404 Not Found` error.
4. The server runs in a loop, handling multiple client requests using separate threads.

## Prerequisites
- Java Development Kit (JDK) 8 or later
- IntelliJ IDEA Ultimate (Recommended for running the project)

## Project Structure
```
|-- src
|   |-- main
|       |-- java
|           |-- SolicitudHttp.java
|           |-- ServidorWebSimple.java
|       |-- resources
|           |-- index.html
|           |-- 404.html
|           |-- cat.jpg
|           |-- cat.gif
```

## Running the Server in IntelliJ IDEA Ultimate
1. Open the project in **IntelliJ IDEA Ultimate**.
2. Ensure that you have the correct SDK set up.
3. Navigate to `ServidorWebSimple.java`.
4. Click on the **Run** button or use the shortcut `Shift + F10` to start the server.
5. The server will start listening on port `6789`.

## Testing the Server
Once the server is running, open a web browser and enter the following URLs:
- `http://localhost:6789/index.html` - Displays the index page.
- `http://localhost:6789/cat.jpg` - Displays the `cat.jpg` image.
- `http://localhost:6789/cat.gif` - Displays the `cat.gif` image.
- `http://localhost:6789/nonexistent.html` - Displays a `404 Not Found` page.

## Notes
- The server serves files located in `src/main/resources`.
- Ensure the requested file exists in the directory to avoid `404` errors.
- The server currently only supports `GET` requests.

