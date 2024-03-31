import fs from 'fs';

async function getAllPosition() {
  return new Promise((resolve, reject) => {
    fs.readFile('./data/zrrdata.json', 'utf8', (err, json) => {

      if (err) {
          console.error('Erreur lors de la lecture du fichier :', err);
          return;
      }
      
      try {

        let zrr = JSON.parse(json);

        if (!zrr || zrr.positionNO == null) {
            throw new Error('Zrr vide');
        }

        resolve(zrr)

      } catch(error) {
        reject(error);
      }
    });
  })  
}

export async function getZrr(options) {
  try {
    const zrr = await getAllPosition();

    return {
      status: '200',
      data: zrr
    };

  } catch(error){
    return {
      status: '404',
      data: "Zrr non défini"
    };
  }
}
