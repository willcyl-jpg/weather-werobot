#!/bin/bash

# Define the name of the JAR file and the Spring Boot application
JAR_FILE="weather-0.0.1-SNAPSHOT.jar"
SPRING_APP_NAME="weather"

# Define the Java executable path
JAVA_HOME="/usr/bin/java"

# Function to start the Spring Boot application
start() {
    echo "Starting $SPRING_APP_NAME..."
    nohup $JAVA_HOME -jar $JAR_FILE > /dev/null 2>&1 &
    echo "$SPRING_APP_NAME started."
}

# Function to stop the Spring Boot application
stop() {
    echo "Stopping $SPRING_APP_NAME..."
    pkill -f $JAR_FILE
    echo "$SPRING_APP_NAME stopped."
}

# Function to view application logs
view_logs() {
    echo "Viewing $SPRING_APP_NAME logs (Press 'q' to exit)..."
    tail -f nohup.out
}

# Function to check application status
status() {
    if pgrep -f "$JAR_FILE" > /dev/null ; then
        echo "$SPRING_APP_NAME is running."
    else
        echo "$SPRING_APP_NAME is not running."
    fi
}

# Function to display available commands
usage() {
    echo "Usage: $0 {start|stop|restart|status|logs}"
    echo "   start   : Start the Spring Boot application"
    echo "   stop    : Stop the Spring Boot application"
    echo "   restart : Restart the Spring Boot application"
    echo "   status  : Check the status of the Spring Boot application"
    echo "   logs    : View the application logs"
}

# Check for command-line argument
case "$1" in
    "start")
        start
        ;;
    "stop")
        stop
        ;;
    "restart")
        stop
        start
        ;;
    "status")
        status
        ;;
    "logs")
        view_logs
        ;;
    *)
        usage
        ;;
esac
