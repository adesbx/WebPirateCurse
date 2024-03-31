import axios from 'axios';
import fs from 'fs';

let globalTtl=60;

async function authenticate(token, origin) {
  return new Promise(async (resolve, reject) => {
  await axios.get(`https://192.168.75.36:8443/users/authenticate`, {
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


async function setZRR(dataZRR) {
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


async function spawnFlask(dataFlask) {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/data.json', 'utf8', (err, data) => {
      if (err) {
        throw new Error(400);
      }
      try {
        const existingData = JSON.parse(data);
        existingData.push(dataFlask);
        const jsonDataFlask = JSON.stringify(existingData, null, 4);
        fs.writeFile('./data/data.json', jsonDataFlask, 'utf8', (err) => {
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
  });
}


async function newFlaskId() {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/data.json', 'utf8', (err, data) => {
      if (err) {
        throw new Error(400);
      }
      try {
        let flasks = JSON.parse(data)
        // Filtrer les objets de rôle "FLASK" et extraire leurs ID
        let flaskIds = flasks.filter(item => item.role === 'FLASK').map(item => item.id);

        // Extraire les nombres des ID et trouver le dernier nombre
        let numbers = flaskIds.map(id => parseInt(id.match(/\d+/)[0])).sort((a, b) => a - b);
        let lastNumber = numbers.length > 0 ? numbers[numbers.length - 1] : 0;
        resolve(lastNumber + 1);
      } catch(error) {
        reject(error);
      }
    });
  });
}

async function verifyRole(login, origin) {
  return new Promise(async (resolve, reject) => {
    await axios.get(`https://192.168.75.36:8443/users/users/${login}`, {
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

async function verifyPositionInZRR(position) {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/zrrdata.json', 'utf8', (err, data) => {
      if (err) {
        throw new Error(400);
      }
      try {
        const zrrdata = JSON.parse(data);
        if(position[0] < zrrdata.positionNE[0] && position[0] > zrrdata.positionSO[0]
          && position[1] < zrrdata.positionNE[1] && position[1] > zrrdata.positionSO[1]) {
            resolve(true);
          } else {
            resolve(false);
          }
      } catch(error) {
        reject(error);
      }
    });
  });
}

export async function postInitZRR(options, origin, token) {
  try {
   const login = await authenticate(token, origin);
   if(await verifyRole(login, origin)) {
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

export async function putTTL(options, origin, token) {
  try {
    const login = await authenticate(token, origin);
    if(await verifyRole(login, origin)) {
      if(options.ttl >= 3) {
        globalTtl = options.ttl;
        return {
          status: '204',
          data: 'nouveau ttl'
        };
      } else {
        throw new Error("le ttl doit être supérieur à 3 secondes");
      }
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


export async function postSpawnFlask(options, origin, token) {
  try {
    const login = await authenticate(token, origin);
    if(await verifyRole(login, origin)) {
      if(await verifyPositionInZRR(options.latLng)) {
        const newId = await newFlaskId();
        const dataFlask = {
            "id": "potion"+newId,
            "position": [
                options.latLng[0],
                options.latLng[1]
            ],
            "role": "FLASK",
            "ttl": globalTtl,
            "potions": 0,
            "terminated": 0,
            "turned": 0
        };
        await spawnFlask(dataFlask);
        return {
          status: '204',
          data: 'nouvelle potion créer'
        };
      } else {
        throw new Error("out of range");
      }
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