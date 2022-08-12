# Deploy Gitea on Fly.io

## Deploy on your own Fly.io account

### 0. Create a Fly.io application (once)

```sh
flyctl launch --generate-name --no-deploy --copy-config
```

or provide an explicit name:

```sh
flyctl launch --generate-name --no-deploy --copy-config --name lambros-gitea
```

### 1. Create the persistent data volume (once)

```sh
flyctl volumes create clouddevdata --region lhr --size 10
```

### 2. Deploy

```sh
flyctl deploy
```

Once the deploy succeeds, visit your Fly.io subdomain for the application and follow the instructions to fully complete the Gitea installation.

For the configuration make sure to **use the Fly.io subdomain (from step 0) where needed instead of the default `localhost`** otherwise you won't be able to visit the site.

Once the setup is done, then you can freely redeploy without resetting the configuration each time.

**Note**

If something went wrong with the configuration and want to start over, login to the container using `flyctl ssh console` and then delete the configuration file `/data/gitea/custom/conf/app.ini`. Then redeploy with `flyctl deploy` and you will get the setup page again.

Or, just delete the app or volume and recreate if you don't have data yet.
