FROM ubuntu:bionic

RUN apt-get update && apt-get install --no-install-recommends -y \
    ca-certificates curl sudo openssh-server bash git \
    iproute2 apt-transport-https gnupg-agent software-properties-common \
    # MARK1 - Install extra packages you need for your dev environment
    htop make vim && \
    apt autoremove -y

ARG USER="clouddevuser"
RUN test -n "$USER"
# Create the user
RUN adduser --disabled-password --gecos '' --home /data/home ${USER}
# passwordless sudo for your user's group
RUN echo "%${USER} ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers

ENV USER_ARG=${USER}

# Setup your SSH server daemon, copy pre-generated keys
RUN rm -rf /etc/ssh/ssh_host_*_key*
COPY etc/ssh/sshd_config /etc/ssh/sshd_config

COPY ./entrypoint.sh ./entrypoint.sh
COPY ./docker-entrypoint.d/* ./docker-entrypoint.d/

ENTRYPOINT ["./entrypoint.sh"]
CMD ["/usr/sbin/sshd", "-D"]

