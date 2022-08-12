# Use the Docker images provided by Litestream and Gitea instead of 
# manually `wget`ing the binaries in the container image during build.
FROM litestream/litestream:0.3.9 as litestream
FROM gitea/gitea:1.16.9 as gitea

# Use a lightweight image for runtime.
FROM alpine:3.16

# Useful packages (e.g. during troubleshooting).
RUN apk add ca-certificates bash vim htop git

# Copy our different configs
COPY config/ /x_config/

# Litestream
COPY --from=litestream /usr/local/bin/litestream /usr/local/bin/litestream

# Gitea
COPY --from=gitea /app/gitea/gitea /usr/local/bin/gitea
## Setup `git` user for Gitea (it doesn't run as root).
ARG USER="git"
RUN adduser --disabled-password --gecos '' -u 1000 --home /data/home ${USER}
RUN echo "%${USER} ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
## Gitea listens on port 3000 by default, and 22 for SSH.
EXPOSE 3000
EXPOSE 22

# Scripts to run
COPY docker-entrypoint.d/ /etc/docker-entrypoint.d/
# RUN chmod +x /etc/docker-entrypoint.d/*

USER ${USER}
ENTRYPOINT ["/etc/docker-entrypoint.d/run.sh"]
