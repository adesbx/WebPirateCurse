import fs from 'fs';
import { resolve } from 'path';

function getAllPosition() {
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

        // console.log(`ZRR-NO ${zrr.positionNO}`);
        // console.log(`ZRR-NE ${zrr.positionNE}`);
        // console.log(`ZRR-SO ${zrr.positionSO}`);
        // console.log(`ZRR-SE ${zrr.positionSE}`);

        resolve(zrr)

      } catch(error) {
        reject(error);
      }
    });
  })  
}

export async function getZrr(options) {
  var data = [{}
  ], status = '200';
  try {
    const zrr = await getAllPosition();

    return {
      status: status,
      data: zrr
    };

  } catch(error){
    return {
      status: 404,
      data: "Zrr vide"
    };
  }
}
