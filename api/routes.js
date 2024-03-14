import resources from './routes/resources.route.js'
import zrr from './routes/zrr.route.js'

export default function (app) {
  /*
  * Routes
  */
  app.use('/resources', resources);
  app.use('/zrr', zrr);

};
