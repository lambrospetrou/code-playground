#!/bin/bash

set -euxo pipefail

echo "Creating /run/sshd"
mkdir -p /run/sshd

HOME_DIR=/data/home
SSH_DIR=/data/etc/ssh

echo "Ensure home directory"
mkdir -p $HOME_DIR

echo "Ensure SSH host keys"
mkdir -p $SSH_DIR
ssh-keygen -A -f /data

echo "Setup SSH access for user $USER_ARG"
mkdir -p $HOME_DIR/.ssh
# Append the given keys to the authorised keys and only keep the uniques!
# The `# empty comment` is to avoid an empty file which causes grep to fail.
echo -e "# empty comment\n$HOME_SSH_AUTHORIZED_KEYS" >> $HOME_DIR/.ssh/authorized_keys
cat $HOME_DIR/.ssh/authorized_keys | sort | uniq | grep -v "^$" > /tmp/authorized_keys
mv /tmp/authorized_keys $HOME_DIR/.ssh/authorized_keys

if [ -f "$HOME_DIR/.ssh/id_ed25519" ]; then
    echo "$HOME_DIR/.ssh/id_ed25519 exists, skipping."
    echo ""
    echo "Make sure you add this public key to your Github / Gitlab / other vcs:"
else 
    echo "$HOME_DIR/.ssh/id_ed25519 does not exist, generating."
    ssh-keygen -t ed25519 -f $HOME_DIR/.ssh/id_ed25519 -C "$USER_ARG@fly-vscode" -N ""
    echo ""
    echo "Add this public key to your Github / Gitlab / other vcs:"
fi
cat $HOME_DIR/.ssh/id_ed25519.pub

echo "chowning your home to you"
chown -R $USER_ARG:$USER_ARG $HOME_DIR

if [[ -d "docker-entrypoint.d" ]]
then
    echo "Running docker-entrypoint.d files"
    /bin/run-parts --verbose docker-entrypoint.d
fi

echo "Running $@"
exec "$@"

