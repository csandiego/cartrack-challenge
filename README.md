## Setup

### 1. Mapbox

Setup Mapbox credentials as described
[here](https://docs.mapbox.com/android/maps/overview/#configure-credentials).
Afterwards, your `$HOME/.gradle/gradle.properties` should have the following:

```
MAPBOX_ACCESS_TOKEN=...
MAPBOX_DOWNLOADS_TOKEN=...
```

*The tokens should be set before attempting to build. `MAPBOX_DOWNLOADS_TOKEN` is needed to
download Mapbox dependencies.*

### 2. NDK

Installing version 21.0.6113669 resulted in successful compilation.
