#!/bin/bash

# Ce Script permet de mettre la bonne Url pour le déployment du front en fonction de l'argument prod ou dev

if [ "$1" == "dev" ]; then
    echo "/**
 * URL de base de l'API à laquelle seront envoyées les requêtes.
 */
const apiBase = 'http://localhost:3376';

export default apiBase;" > ./src/apiBase.js;

elif [ "$1" == "prod" ]; then
    echo "/**
 * URL de base de l'API à laquelle seront envoyées les requêtes.
 */
const apiBase = 'https://192.168.75.36:3376';

export default apiBase;" > ./src/apiBase.js;

else
    echo "need to give argument: <dev> or <prod>";
fi
