import axios from 'axios';
export async function getResources(options) {
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
  var data = [{
    "id": "<string>",
    "position": "<LatLng>",
    "potions": "<integer>",
    "role": "<string>",
    "terminated": "<integer>",
    "ttl": "<integer>",
    "turned": "<integer>",
  }], status = '200';

  return {
    status: status,
    data: data
  };
}
export async function postResourceId(options) {
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
  var data = {}, status = '204';

  return {
    status: status,
    data: data
  };
}
export async function putResourceIdPosition(options) {
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

  var data = {}, status = '204';

  axios.get(`http://192.168.75.36:8080/users/users/${options.resourceId}`)
  .then(function (response) {
    const user = {
      "id": response.data.login,
      "position": options.latLng,
      "role": {
        "species": response.data.species,
        "nombreDeFioles": 0
      }
    }
  
    console.log(user);
  })
  .catch(function (error) {
    console.error(error);
  });

  return {
    status: status,
    data: data
  };
}
