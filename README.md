# Introducing Calendar Manager

Calendar Manager is your go-to solution for efficient Agile event planning. Designed with simplicity and productivity in
mind, it's your companion for managing all your Agile-related activities effortlessly.

Calendar Manager empowers you to:

- _Plan and organize Agile events seamlessly._
- _Keep track of sprint daily, review, refinement, retro, and planning sessions._
- _Ensure every event is associated with the right calendar, eliminating confusion and boosting productivity._

# Requirements

The project requires [Java 17](https://openjdk.org/projects/jdk/17/) or higher.

The project makes use of Maven and uses the [Maven wrapper](https://maven.apache.org/wrapper/), which means you don't
need Maven installed.

# API

Below is a list of API endpoints with their respective input and output. Please note that the application needs to be
running for the following endpoints to work.

## _Calendars_

### Create Calendar

Endpoint

```text
POST /calendars
```

Example of body

```json
{
  "name": "Calendar Name",
  "description": "Calendar Description"
}
```

Parameters

| Parameter     | Description                                   | Type   |
|---------------|-----------------------------------------------|--------|
| `name`        | The name of the calendar (mandatory & unique) | String |
| `description` | The description of the calendar (optional)    | String |

Example of Response

```json
{
  "id": "87736d7a-cb68-4577-947e-7a17980c9bdf",
  "name": "Calendar Name",
  "description": "Calendar Description",
  "events": []
}
```

### Get Calendars

Endpoint

```text
GET /calendars
```

Example of Response

```json
[
  {
    "id": "e567380f-a35a-492a-81af-f58011d85f09",
    "name": "Calendar Name",
    "description": "Calendar Description",
    "events": [
      {
        "id": "281eb51d-e59e-4ca4-920e-5c901ef78ab3",
        "type": "RETRO",
        "name": "Event Name",
        "description": "Event Description",
        "date": "2024-06-01",
        "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
      }
    ]
  }
]
```

### Get Calendar by ID

Endpoint

```text
GET /calendars/<calendarId>
```

Parameters

| Parameter    | Description            | Type |
|--------------|------------------------|------|
| `calendarId` | The ID of the calendar | UUID |

Example of Response

```json
{
  "id": "e567380f-a35a-492a-81af-f58011d85f09",
  "name": "Calendar Name",
  "description": "Calendar Description",
  "events": [
    {
      "id": "281eb51d-e59e-4ca4-920e-5c901ef78ab3",
      "type": "RETRO",
      "name": "Event Name",
      "description": "Event Description",
      "date": "2024-06-01",
      "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
    }
  ]
}
```

### Get Calendar by Name

Endpoint

```text
GET /calendars/name/<calendarName>
```

Parameters

| Parameter      | Description              | Type   |
|----------------|--------------------------|--------|
| `calendarName` | The name of the calendar | String |

Example of Response

```json
{
  "id": "e567380f-a35a-492a-81af-f58011d85f09",
  "name": "Calendar Name",
  "description": "Calendar Description",
  "events": [
    {
      "id": "281eb51d-e59e-4ca4-920e-5c901ef78ab3",
      "type": "RETRO",
      "name": "Event Name",
      "description": "Event Description",
      "date": "2024-06-01",
      "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
    }
  ]
}
```

### Delete Calendars

Endpoint

```text
DELETE /calendars
```

### Delete Calendar by ID

Endpoint

```text
DELETE /calendars/<calendarId>
```

Parameters

| Parameter    | Description            | Type |
|--------------|------------------------|------|
| `calendarId` | The ID of the calendar | UUID |

### Delete Calendar by Name

Endpoint

```text
DELETE /calendars/name/<calendarName>
```

Parameters

| Parameter      | Description              | Type   |
|----------------|--------------------------|--------|
| `calendarName` | The name of the calendar | String |

## _Events_

### Create Events

Endpoint

```text
POST /events
```

Example of body

```json
{
  "type": "RETRO",
  "name": "Event Name",
  "description": "Event Description",
  "startDate": "2024-06-01",
  "endDate": "2024-06-02",
  "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
}
```

Parameters

| Parameter     | Description                                                                        | Type      |
|---------------|------------------------------------------------------------------------------------|-----------|
| `name`        | The type of the events (mandatory & can be DAILY/RETRO/REFINEMENT/PLANNING/REVIEW) | EventType |
| `description` | The description of the events (optional)                                           | String    |
| `startDate`   | The start date of the events (mandatory)                                           | LocalDate |
| `endDate`     | The end date of the events (mandatory)                                             | LocalDate |
| `calendarId`  | The id of the associated calendar (mandatory)                                      | UUID      |

Example of Response

```json
[
  {
    "id": "60431a9e-2d8a-435f-bbc2-abd8ab5fe20c",
    "type": "RETRO",
    "name": "Event Name",
    "description": "Event Description",
    "date": "2024-06-01",
    "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
  },
  {
    "id": "b8716a0a-9039-41a9-9706-dc6b4efbce8e",
    "type": "RETRO",
    "name": "Event Name",
    "description": "Event Description",
    "date": "2024-06-02",
    "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
  }
]
```

### Get Events

Endpoint

```text
GET /events
```

Example of Response

```json
[
  {
    "id": "60431a9e-2d8a-435f-bbc2-abd8ab5fe20c",
    "type": "RETRO",
    "name": "Event Name",
    "description": "Event Description",
    "date": "2024-06-01",
    "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
  },
  {
    "id": "b8716a0a-9039-41a9-9706-dc6b4efbce8e",
    "type": "RETRO",
    "name": "Event Name",
    "description": "Event Description",
    "date": "2024-06-02",
    "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
  }
]
```

### Get Event by ID

Endpoint

```text
GET /events/<eventId>
```

Parameters

| Parameter | Description         | Type |
|-----------|---------------------|------|
| `eventId` | The ID of the event | UUID |

Example of Response

```json

{
  "id": "60431a9e-2d8a-435f-bbc2-abd8ab5fe20c",
  "type": "RETRO",
  "name": "Event Name",
  "description": "Event Description",
  "date": "2024-06-01",
  "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
}
```

### Update Event Date

Endpoint

```text
PUT /events/date
```

Example of body

```json
{
  "id": "60431a9e-2d8a-435f-bbc2-abd8ab5fe20c",
  "newDate": "2024-06-10"
}
```

Parameters

| Parameter | Description                     | Type      |
|-----------|---------------------------------|-----------|
| `id`      | The id of the event (mandatory) | UUID      |
| `newDate` | The new date (mandatory)        | LocalDate |

Example of Response

```json
{
  "id": "60431a9e-2d8a-435f-bbc2-abd8ab5fe20c",
  "type": "RETRO",
  "name": "Event Name",
  "description": "Event Description",
  "date": "2024-06-10",
  "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
}
```

### Update Event Name

Endpoint

```text
PUT /events/name
```

Example of body

```json
{
  "id": "60431a9e-2d8a-435f-bbc2-abd8ab5fe20c",
  "newName": "New Event Name"
}
```

Parameters

| Parameter | Description                     | Type   |
|-----------|---------------------------------|--------|
| `id`      | The id of the event (mandatory) | UUID   |
| `newName` | The new name (mandatory)        | String |

Example of Response

```json
{
  "id": "60431a9e-2d8a-435f-bbc2-abd8ab5fe20c",
  "type": "RETRO",
  "name": "New Event Name",
  "description": "Event Description",
  "date": "2024-06-10",
  "calendarId": "e567380f-a35a-492a-81af-f58011d85f09"
}
```

### Delete Events

Endpoint

```text
DELETE /events
```

### Delete Event by ID

Endpoint

```text
DELETE /events/<eventId>
```

Parameters

| Parameter | Description         | Type |
|-----------|---------------------|------|
| `eventId` | The ID of the event | UUID |