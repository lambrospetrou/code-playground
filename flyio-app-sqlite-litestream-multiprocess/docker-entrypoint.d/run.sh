#!/bin/bash

set -euxo pipefail

# Copy the Gitea app.ini only if it doesn't exist to avoid 
# overwriting our configuration during redeploys.
CUSTOM_CONFIG="/data/gitea/custom/conf/app.ini"
if [ -f "$CUSTOM_CONFIG" ]; then
	echo "Gitea config already exists ($CUSTOM_CONFIG), skipping copying from source (/etc/gitea/conf/app.ini)."
else
	echo "Gitea config was not found ($CUSTOM_CONFIG), copying from source."
	mkdir -p "${CUSTOM_CONFIG%/*}"
	cp /x_configs/gitea_app.ini "$CUSTOM_CONFIG"
fi

# Assumes `gitea` is somewhere in the $PATH.
gitea web -c "$CUSTOM_CONFIG"
