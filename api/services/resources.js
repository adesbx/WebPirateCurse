import axios from 'axios';
import fs from 'fs';

function distance(x1, y1, x2, y2) {
  const X = x2 - x1;
  const Y = y2 - y1;
  return Math.sqrt(X * X + Y * Y);
}

async function verifyUserExist(id) {
  return new Promise((resolve, reject) => {

    fs.readFile('./data/data.json', 'utf8', (err, data) => {
      if (err) {
        throw new Error(400);
      }

      let users = JSON.parse(data);
      const user = users.find(user => user.id === id);

      if (!user || user === undefined ) {
        resolve(false);
      } else {
        resolve(true);
      }
    });
  });
}

async function getAllresources() {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/data.json', 'utf8', (err, json) => {

      if (err) {
          console.error('Erreur lors de la lecture du fichier :', err);
          return;
      }
      
      try {

        let resources = JSON.parse(json);

        if (!resources) {
            throw new Error('resources vide');
        }

        resources = resources.filter(resource => resource.position && resource.position.length > 0);

        resolve(resources)
      } catch(error) {
        reject(error);
      }
    });
  })  
}

async function grabPotion(userSource, potion) {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/data.json', 'utf8', (err, data) => {
      if (err) {
        throw new Error(400);
      }
      try {
        let users = JSON.parse(data);
        const userS = users.find(user => user.id === userSource);
        const userD = users.find(user => user.id === potion);

        if (!userS || !userD) {
          throw new Error(404);
        }

        const [x1, y1] = userS.position.map(Number);
        const [x2, y2] = userD.position.map(Number);
        
        const dist = distance(x1, y1, x2, y2);

        if(userD.role == "FLASK") {
          if (dist <= 5) {
            userS.potions += 1;

            //l'utilisateur prends le ttl de la potion
            userS.ttl = userD.ttl;
    
            //supprimer la potion
            const userDIndex = users.findIndex(user => user.id === potion);
            users.splice(userDIndex, 1);
          } else {
            throw new Error(403);
          }
        } else {
          throw new Error(400)
        }

        let newUser = JSON.stringify(users, null, 4);

        fs.writeFile('./data/data.json', newUser, 'utf8', (err) => {
            if (err) {
                console.error('Erreur écriture dans le fichier :', err);
                return;
            }
        });
        resolve();
      } catch(error) {
        reject(error);
      }
    });
  });
}

async function terminatePirate(userSource, userDestine) {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/data.json', 'utf8', (err, data) => {
    if (err) {
      throw new Error(400);
    }
    try {
      let users = JSON.parse(data);
      const userS = users.find(user => user.id === userSource);
      const userD = users.find(user => user.id === userDestine);

      if (!userS || !userD) {
        throw new Error(404);
      }

      if(userS.role == "VILLAGEOIS" && userD.role == "PIRATE" && userS.potions > 0) {
        userS.potions -= 1;
        userS.terminated += 1;

        //supprimer le pirate
        const userDIndex = users.findIndex(user => user.id === userDestine);
        users.splice(userDIndex, 1);
      } else {
        throw new Error(400)
      }

      let newUser = JSON.stringify(users, null, 4);

      fs.writeFile('./data/data.json', newUser, 'utf8', (err) => {
          if (err) {
              console.error('Erreur écriture dans le fichier :', err);
              return;
          }
      });
      resolve();
    } catch(error) {
      reject(error);
    }
  });
 });
}

async function villagerIntoPirate(userSource, userDestine) {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/data.json', 'utf8', (err, data) => {
      if (err) {
        throw new Error(400);
      }
      try {
        let users = JSON.parse(data);
        const userS = users.find(user => user.id === userSource);
        const userD = users.find(user => user.id === userDestine);

        if (!userS || !userD) {
          throw new Error(404);
        }

        if(userS.role == "PIRATE" && userD.role == "VILLAGEOIS" && userS.potions > 0) {
          userS.potions -= 1;
          userS.turned += 1;
      
          userD.role = "PIRATE";
          userD.terminated = 0;
        } else {
          //TODO mal géré stop le programme
          throw new Error(400)
        }

        let newUser = JSON.stringify(users, null, 4);

        fs.writeFile('./data/data.json', newUser, 'utf8', (err) => {
            if (err) {
                console.error('Erreur écriture dans le fichier :', err);
                return;
            }
        });
        resolve();
      } catch(error) {
        reject(error);
      }
    });
  });
}

async function modifyPosition(id, position) {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/data.json', 'utf8', (err, data) => {
      if (err) {
        throw new Error(400);
      }

      let users = JSON.parse(data);
      const user = users.find(user => user.id === id);

      if (!user) {
        throw new Error(404);
      }

      try {
        user.position = position; 

        let newUser = JSON.stringify(users, null, 4);
    
        fs.writeFile('./data/data.json', newUser, 'utf8', (err) => {
            if (err) {
                console.error('Erreur écriture dans le fichier :', err);
                return;
            }
        });
        resolve();
      } catch(error) {
        reject(error);
      }
    });
  });
}

export async function getResources(options, origin, token) {
  try {
    const login = await axios.get(`http://localhost:8080/authenticate`, {
      params: {
        jwt: token,
        origin: origin
      }
    })
    .then(function (response) {
      return response.data;
    })
    .catch(function() {
      throw new Error(401);
    });
    
    const resources = await getAllresources();

    return {
      status: '200',
      data: resources
    };

  } catch(error){
    let statusCode = parseInt(error.message);
    switch(statusCode){
      case 401:
        return {
          status: '401',
          data: 'Authentification failed'
        };
      default:
        return {
          status: '400',
          data: error.message
        };
    }
  }
}

export async function postResourceId(options, origin, token) {
  try {
    const login = await axios.get(`http://localhost:8080/authenticate`, {
      params: {
        jwt: token,
        origin: origin
      }
    })
    .then(function (response) {
      return response.data;
    })
    .catch(function() {
      throw new Error(401);
    });

    if(await verifyUserExist(options.resourceId)){

      switch(options.operationType.operationType) {
        case "grab potion flask":
          await grabPotion(login, options.resourceId);
          break
        case "terminate pirate":
          await terminatePirate(login, options.resourceId);
          break
        case "turn villager into pirate":
          await villagerIntoPirate(login, options.resourceId);
          break
        default:
          console.log("default");
          break
      }

      return {
        status: '204',
        data: 'Opération réalisé'
      };

    } else {
      throw new Error(404);
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
          data: 'User trop loin'
        };
      }
      case 404:
        return {
          status: '404',
          data: 'resource pas trouvé'
        };
      default:
        return {
          status: '400',
          data: 'Problème sur l\'opération'
        };
    }
  }
}

export async function putResourceIdPosition(options, origin, token) {
  try {
    const login = await axios.get(`http://localhost:8080/authenticate`, {
      params: {
        jwt: token,
        origin: origin
      }
    })
    .then(function (response) {
      return response.data;
    })
    .catch(function() {
      throw new Error(401);
    });

    if(await verifyUserExist(options.resourceId)) {
      if (login === options.resourceId) {
        await modifyPosition(options.resourceId, options.latLng);
        return {
          status: '204',
          data: 'Position modifié'
        };
      } else {
        throw new Error(403);
      }
    } else {
      throw new Error(404);
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
