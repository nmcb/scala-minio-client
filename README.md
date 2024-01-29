## Scala-minio

Provides an AWS S3 compliant Scala 3 client for MinIO infra-structures.

## Local development

Local development requires a locally running MioIO installation.

### Prerequisites

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

PRs are appreciated. During day to day development and to test local changes, start
the MinIO server locally:

```shell
minio server ~/data --console-address localhost:9001
```

And when you call it a day, don't forget to stop the MinIO server, and commit your code:

```shell
mc admin service stop local
```

---

ᓚᘏᗢ ... ᘛ⁐̤ᕐᐷ