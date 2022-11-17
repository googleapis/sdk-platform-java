# Showcase Integration Testing

## Requirements

* Install `go` in your `PATH`

## Scripts

* `install.sh <version>`

  Install the given version of the showcase server.
* `start.sh`

  Starts the installed showcase server.
* `stop.sh`

  Stops any running showcase server.

* `verify.sh <version>`

  Invokes `install.sh <version>`,`start.sh`, `mvn verify`, then `stop.sh`

## Development

Recommended workflow:

* Invoke `install.sh <version>` and `start.sh`
* Invoke `mvn verify` multiple times while iterating.
* Invoke `stop.sh` when finished.
