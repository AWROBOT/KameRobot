#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ESP8266WebServer.h>

#include "minikame.h"

#define INIT      0
#define WALK      1
#define RUN       2
#define LEFT      3
#define RIGHT     4
#define STOP      5
#define HELLO     6
#define UPDOWN    7
#define DANCE     8
#define PUSHUP    9
#define FRONTBACK 10
#define MOONWALK  11

MiniKame robot;

bool running = 0;
bool inited = 0;

String input;

/* Set these to your desired credentials. */
const char *ssid = "KameRobot";
const char *password = "awrobot";

ESP8266WebServer server(80);

void setup() 
{
  delay(1000);
  
  Serial.begin(115200);
  Serial.println();
  Serial.print("Configuring access point...");
  /* You can remove the password parameter if you want the AP to be open. */
  WiFi.softAP(ssid, password);

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    Serial.print(".");
  }
  
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.softAPIP()); 

  server.on("/", handleRoot);

  server.on("/commands", handleCommands);
  
  server.onNotFound ( handleNotFound );

  server.begin();
  Serial.println("HTTP server started");
}

void loop() 
{
  server.handleClient();

  if(inited)
  {
    if (running) //Keep moving
    {
      parseData(input);
    }
    else
    {
      robot.home();
    }
  }
}

void handleRoot() 
{
  server.send(200, "text/html", "<h2>Connected to KameRobot!</h2>");
}

void handleCommands() 
{
  String message = "";

  String data = server.arg("data");

  if (data == "") //Parameter not found
  {     
    message = "Data Argument Not Found!";
  }
  else //Parameter found
  {     
    message = "Data Argument = ";
    message += data;     //Gets the value of the query parameter
    
    parseData(data);

    input = data;
  }

  server.send(200, "text/plain", message); //Returns the HTTP response
}

void handleNotFound() 
{
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += ( server.method() == HTTP_GET ) ? "GET" : "POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";

  server.send ( 404, "text/plain", message );
}

void parseData(String data)
{
    switch (data.toInt())
    {
        case INIT:
            Serial.println("init");
            robot.init();
            inited = 1;
            break;
      
        case WALK:
            Serial.println("walk");
            robot.walk(1,550);
            running = 1;
            break;

        case RUN:
            Serial.println("run");
            robot.run(1,550);
            running = 1;
            break;

        case LEFT:
            Serial.println("left");
            robot.turnL(1,550);
            running = 1;
            break;

        case RIGHT:
            Serial.println("right");
            robot.turnR(1,550);
            running = 1;
            break;

        case STOP:
            Serial.println("stop");
            running = 0;
            break;

        case HELLO:
            Serial.println("hello");
            robot.hello();
            running = 0;
            break;

        case UPDOWN:
            Serial.println("updown");
            robot.upDown(4,250);
            running = 0;
            break;

        case DANCE:
            Serial.println("dance");
            robot.dance(2,1000);
            running = 0;
            break;

        case PUSHUP:
            Serial.println("pushup");
            robot.pushUp(2,2000);
            running = 0;
            break;

        case FRONTBACK:
            Serial.println("frontback");
            robot.frontBack(4,200);
            running = 0;
            break;

        case MOONWALK:
            Serial.println("moonwalk");
            robot.moonwalkL(2,1000);
            running = 0;
            break;        

        default:
            Serial.println("home");
            robot.home();
            running = 0;
            break;
    }
}
