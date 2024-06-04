# Better and simpler logging for your Mulesoft Applications (java 8, 11 and 17)

Get logging working in minutes with beautiful formating and extensability.

Sample logging statement:

```
{
  "message" : "Starging flow",
  "timestamp" : "2024-06-03 20:36:05:891Z",
  "correlationId" : "6db8ce20-220a-11ef-bdde-623e5ff3fb64",
  "level" : "TRACE",
  "environment" : "Dev",
  "application" : "test-proc",
  "content" : "{\n  \"testingMyPayload\": \"test payload \"\n}"
}
```

## How to use it:

### Build & install component in your exchange:

1. In the pom.xml, replace groupId ORGANIZATION_ID by your own mule organization id.

<img width="358" alt="image" src="https://github.com/javabr/javabr-logging/assets/1243385/384febc6-7972-4c8b-b545-a25f2ba12003">

To something like:

<img width="494" alt="image" src="https://github.com/javabr/javabr-logging/assets/1243385/41ec9ada-bb99-405b-8906-c2014d2b3d2e">
     

2. Package and deploy javabr-logging artifact to your exchange:

   ```
   mvn clean deploy
   ```
   
### Usage:

1. Add the javabr-logging dependency to your pom.xml:

```
    <dependency>
      <groupId>[YOUR ORGANIZATION ID USED IN THE STEP ABOVE]</groupId>
      <artifactId>javabr-logging</artifactId>
      <version>1.0.0</version>
      <classifier>mule-plugin</classifier>
    </dependency>
```

2. From the Mule palette, drag a Log operation into your flow and start configuring it:


![image](https://github.com/javabr/javabr-logging/assets/1243385/16c3349f-6c63-4886-9c94-c02b78d5e0fe)


3. You will need to fill out the Log Settings:

![image](https://github.com/javabr/javabr-logging/assets/1243385/c04810b6-ace6-4297-b360-a3572020c76d)


```
     <javabr-logging:config 	name="Javabr_logging_Config"
       artifactId="test" applicationName="${app.name}"
       muleEnvironmentName="${mule.env}" />
```

The field artifactId should match your pom.xml artifactId. This is not mandatory, but adds extra clarity and filtering capabilities to your logging.

4. Then you can populate the **Log's** fields:

![image](https://github.com/javabr/javabr-logging/assets/1243385/c51e2077-baf5-487b-bf3f-d453a3ee3f4c)

5. The last step is to make sure your log4j2.xml file contains a AsyncLogger node that matches the **artifactId** in your javabr log configuration:

![image](https://github.com/javabr/javabr-logging/assets/1243385/aeb1b714-67fd-43d2-b746-7b48eb22f1c6)


```
     <AsyncLogger name="test-process" level="INFO">
          <AppenderRef ref="file"/>
     </AsyncLogger>
```
