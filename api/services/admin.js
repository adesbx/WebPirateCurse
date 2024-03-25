import axios from 'axios';
import { json } from 'express';
import fs from 'fs';
import { resolve } from 'path';

function authenticate(token, origin) {
  return new Promise(async (resolve, reject) => {
  await axios.get(`http://localhost:8080/authenticate`, {
      params: {
        jwt: token,
        origin: origin
      }
    })
    .then(function (response) {
      resolve(response.data);
    })
    .catch(function() {
      reject(new Error(401));
    });
  })
}


function setZRR(dataZRR) {
  return new Promise((resolve, reject) => {
    try {
      const jsonDataZRR = JSON.stringify(dataZRR, null, 4);
      fs.writeFile('./data/zrrdata.json', jsonDataZRR, { encoding: 'utf8', flag: 'w' }, (err) => {
          if (err) {
              console.error('Erreur écriture dans le fichier :', err);
              reject(err);
          }
      });
      resolve();
    } catch(error) {
      reject(error);
    }
  });
}

function verifyRole(login, origin) {
  return new Promise(async (resolve, reject) => {
    await axios.get(`http://localhost:8080/users/${login}`, {
      headers: {
        Origin: origin
      }
    })
    .then(function(response) {
      if(response.data.species == 'ADMIN') {
        resolve(true);
      } else {
        resolve(false);
      }
    })
    .catch(function() {
      reject(new Error(404));
    })
  })
}

export async function postInitZRR(options, origin, token) {
  try {
   const login = await authenticate(token, origin);
   if(verifyRole(login, origin)) {
    let SO,NE,SE,NO;
    if(options.latLng1[0]<options.latLng2[0]) { // c'est O
      if(options.latLng1[1]<options.latLng2[1]) { // c'est SO
        SO = options.latLng1;
        NE = options.latLng2;
        SE = [options.latLng2[0], options.latLng1[1]];
        NO = [options.latLng1[0], options.latLng2[1]];
      } else { // c'est NO
        SO = [options.latLng1[0], options.latLng2[1]];
        NE = [options.latLng2[0], options.latLng1[1]];
        SE = options.latLng2;
        NO = options.latLng1;
      }
    } else { // c'est E
      if(options.latLng1[1]<options.latLng2[1]) { // c'est SE
        SO = [options.latLng2[0], options.latLng1[1]];
        NE = [options.latLng1[0], options.latLng2[1]];
        SE = options.latLng1;
        NO = options.latLng2;
      } else { // c'est NE
        SO = options.latLng2;
        NE = options.latLng1;
        SE = [options.latLng1[0], options.latLng2[1]];
        NO = [options.latLng2[0], options.latLng1[1]];
      }
    }

    const data = {
      "positionNO": NO,
      "positionNE": NE,
      "positionSE": SE,
      "positionSO": SO
    }
    await setZRR(data);
    return {
      status: '204',
      data: 'initialisation ZRR faite'
    };

   } else {
     throw new Error(403)
   }
  } catch(error) {
    let statusCode = parseInt(error.message);
    switch(statusCode){
      case 401:
        return {
          status: '401',
          data: 'Authentification failed'
        };
      case 403: {
        return {
          status: '403',
          data: 'Pas authorisé'
        };
      }
      case 404:
        return {
          status: '404',
          data: 'User pas trouvé'
        };
      default:
        return {
          status: '400',
          data: error.message
        };
    }
  }
}