# Search Queries and Filters Code Sample

**Quick Start:**  Run a docker container and deploy the search queries and filters sample to it:

1. Pull the [Docker tag](https://hub.docker.com/r/liferay/portal/tags?page=1&ordering=last_updated) for the version of portal you want to run:

    ```
    docker pull liferay/portal:7.4.2-ga3
    ```

1. Once it's downloaded, create and run a container from the tag:

    ```
    docker run -it \
            -p 8080:8080 -p 8000:8000 -p 11311:11311 \
            --env LIFERAY_JPDA_ENABLED=true \
            --name lrdev liferay/portal:7.4.2-ga3
    ```
1. Once Liferay is started started, deploy the sample by running

    ```
    ./gradlew deploy -Ddeploy.docker.container.id=lrdev
    ```

## Using the Sample

To test the queries and filters sample,

1. After Liferay is started, pre-populate it with content to be searched.

    Go to the src/main/resources directory in the sample module (<./queries-and-filters-osgi-command/src/main/resources/headless-demo-content/>) and run

    ```
    sh DocumentFolder_Post_ToSite.sh
    ```

1. Open the Gogo shell and search using the command

    ```
    liferay:search able
    ```

    Only documents with the `localized_title_en_US` field matching the `able` keyword, AND having a `folderId` of `0`, are returned. Examine the Java class to see how this query was constructed and executed.

## About the Sample

**Goal:** Provide a code sample that demonstrates, as simply as possible (while still being useful) writing code using the Search APIs.

**Technical Overview:** The project is a Liferay Workspace, very similar to the samples found in the [Liferay Learn](https://github/com/liferay/liferay-learn) repository. There the projects can be found in `liferay-xxxx.zip` folders, which are zipped up and uploaded to Liferay Learn as part of the build process. In addition to Java code that shows how to use the search APIs, the sample includes a shell script (in the `src/main/resources` folder) that can be run to pre-populate the running portal with content.
