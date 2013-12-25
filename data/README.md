# JSON Transcription of Space Alert Missions.

## Transcription Notes

* Note that the mission cards mark the time for the 5-second warning to the end
  of the phase, but not explicitly when the next phase begins. The transcribed
  events are thus 5 seconds after the end-of-phase warning time.
* Data Transfers are marked from when the sound starts (i.e. trading can begin);
  their duration is always 5 seconds until the beep.
* Ends of phases are all warned at one minute, 20 seconds and 5 seconds
  remaining. These warnings are not transcribed since they can be derived.
* Communication restoration is not a second event, but rather a derived
  property from the communications down event.
* Phases are marked by their end times, which are the same as the next phase's
  beginning time. This makes a more uniform data representation.
* Data is **always in chronological order**.

## Object Definitions

### Event

The base properties of an event.

* **time**: The time in seconds of the event, since the beginning of the mission.
* **event**: The event type.

### Threat

The *threat* type event.

* **t**: The offset of the event (e.g. T+6), as an integer.
* **zone**: One of white, red, blue, or internal.
* **unconfirmed**: True if the threat is unconfirmed; defaults to false.
* **serious**: True if the threat is serious; defaults to false.

### Incoming Data

The *incoming_data* type event; has no additional properties.

### Data Transfer

The *data_transfer* type event; has no additional properties.

### Communications Down

The *communications_down* type event

* **duration**: Duration of the communications down event, in seconds.

### End Phase
The *end_phase* event; this is the same time as the beginning of the next phase
* **phase**: The phase number being ended.
