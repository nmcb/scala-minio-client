## Scala-minio

Provides an AWS S3 compliant Scala 3 client for MinIO infra-structures.

## Local development

For local development you probably want a locally running MioIO installation, the integration tests require a docker daemon.

### Prerequisites

To install a docker daemon locally, e.g. for Mac OS X follow the installation [instructions](https://docs.docker.com/desktop/install/mac-install/).

To install MinIO locally, e.g. via `brew`:

```shell
brew install minio/stable/minio
```

To install MinIO CLI tooling locally, e.g. via `brew`:

```shell
brew install minio/stable/mc
```

To make an alias to the local MinIO installation:

```shell
mc alias set local http://localhost:9000 minioadmin minioadmin
``` 

To keep your local MinIO installation up to date:

```shell
mc admin update local
``` 

### Pull Requests

PRs are appreciated. During day to day development with local changes, you probably want to start a MinIO server locally:

```shell
minio server ~/data --console-address localhost:9001
```

And when you call it a day, don't forget to stop the MinIO server, and commit your code:

```shell
mc admin service stop local
```

We test the client in memory for which only a docker agent is required. Make sure you stop the minio server as explained above and start the docker daemon. E.g. on Mac OS X start the docker desktop application to make sure the background daemon is running.

---

ᓚᘏᗢ ... ᘛ⁐̤ᕐᐷ