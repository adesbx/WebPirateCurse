import fs from 'fs';
export async function getZrr(options) {
  // Implement your business logic here...
  //
  // Return all 2xx and 4xx as follows:
  //
  // return {
  //   status: 'statusCode',
  //   data: 'response'
  // }

  // If an error happens during your business logic implementation,
  // you can throw it as follows:
  //
  // throw new Error('<Error message>'); // this will result in a 500
  var data = [{}
  ], status = '200';

  fs.readFile('./data/zrrdata.json', 'utf8', (err, json) => {

    if (err) {
        console.error('Erreur lors de la lecture du fichier :', err);
        return;
    }

    let zrr = JSON.parse(json);
    console.log(zrr);
    if (!zrr) {
        console.error('zrr vide');
        return;
    }

    console.log(`ZRR-NO ${zrr.positionNO}`);
    console.log(`ZRR-NE ${zrr.positionNE}`);
    console.log(`ZRR-SO ${zrr.positionSO}`);
    console.log(`ZRR-SE ${zrr.positionSE}`);
    
  data = zrr;
  });
  console.log(data);
  return {
    status: status,
    data: data
  };
}
