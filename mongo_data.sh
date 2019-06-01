#! /bin/sh

MONGO_USER=testadmin
MONGO_PWD=testadmin
MONGO_VOICE_DB=voicecloud
MONGO_HOST=master2.k8s.voiceaitech.com
PORT=48017
MONGO_SERVER=mongo://${MONGO_USER}:${MONGO_PWD}@${MONGO_HOST}:${PORT}/${MONGO_COLLECTION}

echo "configuration: mongo server ==> $MONGO_SERVER"

mongo ${MONGO_SERVER} << EOF

show dbs
use ${MONGO_VOICE_DB}
for coll in db.getCollection():
    echo coll

EOF
