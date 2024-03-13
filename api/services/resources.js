module.exports = {
  /**
  * Returns an array containing the representations of all existing resources in the game


  */
  getResources: async (options) => {

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
      }],
      status = '200';

    return {
      status: status,
      data: data
    };  
  },

  /**
  * Villagers and pirates can grab potion flasks, villager can eliminate pirates, or pirate can turn villagers into pirates
  * @param options.resourceId resource ID 

  */
  postResourceId: async (options) => {

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

    var data = {},
      status = '204';

    return {
      status: status,
      data: data
    };  
  },

  /**
  * Send a LatLng object to the server.
  * @param options.resourceId User&apos;s login 

  */
  putResourceIdPosition: async (options) => {

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

    var data = {},
      status = '204';

    return {
      status: status,
      data: data
    };  
  },
};
