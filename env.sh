#!/bin/bash
set -v

export ASTRA_CLUSTER_ID=f2aa8aba-0308-4caf-a96e-b37d9c343f94
export ASTRA_CLUSTER_REGION=us-east1
export ASTRA_DB_NS=kawoosh
export ASTRA_DB_USERNAME=kawoosh
export ASTRA_DB_PASSWORD=kawoosh
export ASTRA_AUTHORIZATION_TOKEN=e8170076-96c6-472f-86ef-c3924c599e7d

# BASH Is life

### get token auth

## WORKING YEAY
#curl --request POST --url "https://${ASTRA_CLUSTER_ID}-${ASTRA_CLUSTER_REGION}.apps.astra.datastax.com/api/rest/v1/auth" --header 'accept: */*' --header 'content-type: application/json' --header "x-cassandra-request-id: `uuidgen`" --data '{"username":"'"${ASTRA_DB_USERNAME}"'", "password":"'"${ASTRA_DB_PASSWORD}"'"}'


#curl --request POST --url https://${ASTRA_CLUSTER_ID}-${ASTRA_CLUSTER_REGION}.apps.astra.datastax.com/api/rest/v2/namespaces/${ASTRA_DB_NS}/collections/faaa --header 'accept: */*' --header 'content-type: application/json' --header "x-cassandra-request-id: `uuidgen`" --header "x-cassandra-token: ${ASTRA_AUTHORIZATION_TOKEN}" --data '{ "id": "BISOUS", "uuid":"4aa39c79-2439-46b6-87f4-6b1b55841ad4", "documentId": "my-first-post-a6h54","title": "Hello World","author": {"name": "Cliff Wicklow"}}'


#curl --request PUT --url "https://${ASTRA_CLUSTER_ID}-${ASTRA_CLUSTER_REGION}.apps.astra.datastax.com/api/rest/v2/namespaces/${ASTRA_DB_NS}/collections/faaa/blabliblou" --header 'accept: */*' --header 'content-type: application/json' --header "x-cassandra-request-id: `uuidgen`" --header "x-cassandra-token: ${ASTRA_AUTHORIZATION_TOKEN}" --data '{ "id": "BISOUS", "uuid":"4aa39c79-2439-46b6-87f4-6b1b55841ad4", "documentId": "my-first-post-a6h54","title": "Hello World","author": {"name": "Cliff Wicklow"}}'

curl --request GET --url https://${ASTRA_CLUSTER_ID}-${ASTRA_CLUSTER_REGION}.apps.astra.datastax.com/api/rest/v2/namespaces/${ASTRA_DB_NS}/collections/faaa/blabliblou --header 'accept: */*' --header 'content-type: application/json' --header "x-cassandra-request-id: `uuidgen`" --header "x-cassandra-token: ${ASTRA_AUTHORIZATION_TOKEN}"

#curl --request PATCH --url https://${ASTRA_CLUSTER_ID}-${ASTRA_CLUSTER_REGION}.apps.astra.datastax.com/api/rest/v2/namespaces/${ASTRA_DB_NS}/collections/faaa/blabliblou --header 'accept: */*' --header 'content-type: application/json' --header "x-cassandra-request-id: `uuidgen`" --header "x-cassandra-token: ${ASTRA_AUTHORIZATION_TOKEN}" --data '{"author2": {"name": "Cliff Wicklow"}}'
