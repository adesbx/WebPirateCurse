export async function getAllPosition() {
  return new Promise((resolve, reject) => {
    try {
      if (!global.zrr || global.zrr.positionNO == null) {
          throw new Error('Zrr vide');
      }

      resolve(global.zrr);

    } catch(error) {
      reject(error);
    }
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